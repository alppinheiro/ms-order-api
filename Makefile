# Nome e tag da imagem
IMAGE_NAME=ms-order-api
IMAGE_TAG=0.0.1
NAMESPACE=ms-order

NAMESPACE_MS_ORDER=ms-order
NAMESPACE_MONITORING=monitoring
SERVICE_APP=ms-order-api
SERVICE_PROMETHEUS=kube-prometheus-stack-prometheus
SERVICE_GRAFANA=kube-prometheus-stack-grafana

# Ativa o ambiente docker do Minikube
docker-env:
	eval $(minikube docker-env)

# Constrói a imagem dentro do ambiente Docker do Minikube
build: docker-env
	docker build -t $(IMAGE_NAME):$(IMAGE_TAG) .

# Aplica os manifests YAML (namespace, deployment, service)
apply:
	kubectl apply -f namespace.yml
	kubectl apply -f deployment.yml
	kubectl apply -f service.yml

# Combina tudo: build da imagem + apply dos YAMLs
deploy: build apply

# Verifica os pods no namespace ms-order
status:
	kubectl get pods -n $(NAMESPACE)

# Limpa recursos (deployment, service e namespace)
clean:
	kubectl delete -f deployment.yml --ignore-not-found
	kubectl delete -f service.yml --ignore-not-found
	kubectl delete -f namespace.yml --ignore-not-found

# Força rebuild da imagem
rebuild:
	docker rmi $(IMAGE_NAME):$(IMAGE_TAG) || true
	make deploy

.PHONY: all app-status prometheus grafana logs

# Exibe status dos pods e serviços
all:
	kubectl get pods -A
	kubectl get svc -A

# Verifica status da aplicação via /actuator/health
app-status:
	@echo "Aguardando serviço da aplicação expor endpoint..."
	sleep 3
	curl -s http://$$(minikube ip):$$(kubectl get svc $(SERVICE_APP) -n $(NAMESPACE_MS_ORDER) -o=jsonpath='{.spec.ports[0].nodePort}')/actuator/health | jq

# Redireciona porta do Prometheus localmente
prometheus:
	kubectl port-forward -n $(NAMESPACE_MONITORING) svc/$(SERVICE_PROMETHEUS) 9090:9090

# Redireciona porta do Grafana localmente
grafana:
	kubectl port-forward -n $(NAMESPACE_MONITORING) svc/$(SERVICE_GRAFANA) 3000:80

# Verifica logs da aplicação
logs:
	kubectl logs -n $(NAMESPACE_MS_ORDER) deployment/$(SERVICE_APP) --tail=100 -f