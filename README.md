# ms-order-api

Resumo rápido

Microserviço em Kotlin com Spring Boot para gerenciamento de pedidos (orders). Implementa endpoints REST para criação e consulta de pedidos, métricas com Micrometer/Prometheus e integração opcional com PostgreSQL via JPA/Flyway. O projeto segue um estilo Ports & Adapters (Hexagonal): casos de uso na camada de aplicação, lógica de negócio no domínio e adaptadores para entrada (REST) e saída (repositórios, métricas).

O README foi atualizado para refletir as mudanças recentes:
- O endpoint POST /orders agora retorna HTTP 201 Created com header Location: /orders/{id}.
- Validação de payloads via Jakarta Bean Validation (Hibernate Validator) está ativa; requisições inválidas retornam 400 com JSON estruturado (veja seção "Validação e erros").
- Repositório in-memory (`PedidoRepositoryInMemory`) gera IDs sequenciais e está ativo por padrão como profile `default`/`inmemory`.
- Para execuções locais de teste recomendamos desabilitar o restart automático do DevTools (variável de ambiente demonstrada abaixo).

Checklist do que este README cobre

- [x] O que o projeto faz
- [x] Arquitetura e organização do código
- [x] Tecnologias usadas
- [x] Endpoints e exemplos de payload (atualizados)
- [x] Como rodar (modo rápido com repositório em memória / com PostgreSQL)
- [x] Docker / docker-compose / Kubernetes (Makefile)
- [x] Observabilidade (Actuator, Prometheus, OpenTelemetry)
- [x] Migrações (Flyway)
- [x] Testes e próximos passos

Visão geral - o que o projeto faz

O serviço expõe uma API REST com os principais recursos para gerenciar pedidos:

- Criar pedido: POST /orders
- Consultar pedido: GET /orders/{id}

Ao criar um pedido são calculados os totais a partir dos itens informados e o pedido é persistido. Por padrão a aplicação usa um repositório em memória (`PedidoRepositoryInMemory`), mas há implementação JPA (Postgres) disponível e com Flyway para migrações.

Arquitetura

O projeto segue o padrão Ports & Adapters (Hexagonal):

- domain: modelos de domínio (`Pedido`, `ItemPedido`, `Cliente`, `Endereco`, `StatusPedido`) e serviços/ports.
- application/usecase: casos de uso (interfaces) como `CriarPedidoUseCase` e `ConsultarPedidoUseCase`.
- infrastructure/adapter/input: controllers REST (adaptadores de entrada), mapeadores de request/response.
- infrastructure/adapter/output: repositórios (implementação in-memory e JPA), mapeadores de entidade <-> domínio e métricas.
- infrastructure/config: configurações (beans, datasources, Flyway)

Principais pacotes

- `com.order.api.application.usecase` - interfaces de casos de uso
- `com.order.api.domain` - modelos e serviços de negócio
- `com.order.api.infrastructure.adapter.input.controller` - controllers e DTOs
- `com.order.api.infrastructure.adapter.output.repository` - repositórios e entidades JPA
- `com.order.api.infrastructure.config` - configurações de DataSource e Beans

Tecnologias

- Kotlin 1.9 + Java 17
- Spring Boot 3.4.x (web, actuator, data-jpa)
- Maven (com wrapper `./mvnw` disponível)
- PostgreSQL (dependência runtime)
- Flyway (migrations em `src/main/resources/db/migration`)
- HikariCP
- Micrometer + micrometer-registry-prometheus
- OpenTelemetry (OTLP exporter)
- Docker (Dockerfile) e Docker Compose (em `docker-local/`)
- Kubernetes manifests em `k8s/`
- Jakarta Bean Validation (Hibernate Validator) — validação de payloads ativada

Endpoints (Resumo)

1) Criar pedido

- URL: POST /orders
- Request body (exemplo):

```json
{
  "cliente": {
    "nome": "João Silva",
    "email": "joao@example.com",
    "cpf": "123.456.789-00"
  },
  "enderecoEntrega": {
    "rua": "Rua A",
    "numero": "100",
    "complemento": "Apto 10",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01000-000"
  },
  "itens": [
    { "produto": "Caneca", "quantidade": 2, "precoUnitario": 25.50 },
    { "produto": "Camiseta", "quantidade": 1, "precoUnitario": 59.90 }
  ]
}
```

- Exemplo de curl (criar):

```bash
curl -i -X POST http://localhost:8080/orders \
  -H 'Content-Type: application/json' \
  -d '{"cliente": {"nome":"João","email":"joao@example.com","cpf":"000"},"enderecoEntrega":{"rua":"Rua","numero":"1","complemento":null,"bairro":"B","cidade":"C","estado":"S","cep":"00000-000"},"itens":[{"produto":"X","quantidade":1,"precoUnitario":10.0}] }'
```

- Resposta: HTTP 201 Created com header `Location: /orders/{id}` e body JSON com o pedido criado (campos: id, dataCriacao, cliente, enderecoEntrega, itens, valorTotal, status).

2) Consultar pedido

- URL: GET /orders/{id}
- Exemplo de curl:

```bash
curl -s http://localhost:8080/orders/1
```

- Resposta: HTTP 200 com o pedido ou 404 se não encontrado.

Validação e erros

- Payloads são validados automaticamente com Jakarta Bean Validation (@Valid / @NotBlank / @Email / @Positive / @NotEmpty).
- Erros de validação retornam HTTP 400 com um JSON estruturado semelhante a:

```json
{
  "timestamp": "2025-11-11T02:39:42.886Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Requisição inválida. Verifique os campos e tente novamente.",
  "errors": [
    { "field": "cliente.email", "message": "deve ser um endereço de e-mail bem formado" },
    { "field": "enderecoEntrega.rua", "message": "não deve estar em branco" }
  ]
}
```

Observações:
- As mensagens exatas podem vir do provider de validação; se preferir mensagens customizadas em PT-BR posso adicionar um arquivo `ValidationMessages_pt_BR.properties` e chaves de mensagem.

Persistência e migrações

- Há arquivos de migração SQL localizados em `src/main/resources/db/migration` (`V1__...` até `V5__...`).
- `PedidoRepositoryInMemory` gera ids sequenciais e está ativo para os profiles `inmemory` e `default`.
- `PedidoRepositoryImpl` implementa a persistência via JPA (Postgres) e tenta resolver clientes existentes por email para evitar duplicidade. Flyway é usado para as migrações quando o profile `db` está ativo.

Executando o projeto

Modo rápido (sem banco)

1) Build e run com Maven (usa repositório em memória):

```bash
./mvnw clean package -DskipTests
java -jar target/ms-order-api-0.0.1-SNAPSHOT.jar
```

2) Acesse a API em http://localhost:8080

Modo com PostgreSQL (local / docker-compose)

1) Usando o `docker-local/docker-compose.yml` (inclui Postgres, Prometheus, Grafana, Tempo):

```bash
cd docker-local
docker compose up -d
```

2) Ajuste as variáveis de ambiente (exemplo usado nos meus testes):

```bash
export SPRING_DATASOURCE_WRITE_URL="jdbc:postgresql://localhost:5432/order_db"
export SPRING_DATASOURCE_READ_URL="$SPRING_DATASOURCE_WRITE_URL"
export SPRING_DATASOURCE_WRITE_USERNAME="user"
export SPRING_DATASOURCE_READ_USERNAME="user"
export SPRING_DATASOURCE_WRITE_PASSWORD="password"
export SPRING_DATASOURCE_READ_PASSWORD="password"
export SPRING_PROFILES_ACTIVE=db
# desabilitar restart automático do devtools para execuções locais:
export SPRING_DEVTOOLS_RESTART_ENABLED=false
./mvnw spring-boot:run
```

3) Flyway: as migrações serão aplicadas automaticamente quando o `profile=db` estiver ativo (ver logs do Flyway). Se preferir, altere `spring.flyway.enabled` no `application-db.yml`.

Docker / Imagem

Há um `Dockerfile` multi-stage que constrói a aplicação com Maven e empacota o jar. Para buildar localmente:

```bash
docker build -t ms-order-api:0.0.1 .
```

Kubernetes

Manifestos estão na pasta `k8s/` (deployment, service, namespace). O `Makefile` contém alvos úteis:

- `make build` — constrói a imagem no daemon Docker do Minikube
- `make deploy` — aplica os manifests (namespace, deployment, service)
- `make logs` — acompanhar logs do deployment

Observabilidade

- Spring Actuator expõe endpoints: /actuator/health, /actuator/info, /actuator/metrics e /actuator/prometheus conforme `application.yml`.
- Micrometer + Prometheus: dependência `micrometer-registry-prometheus` já está presente e as métricas estão expostas em `/actuator/prometheus`.
- OpenTelemetry: `otel.exporter.otlp.endpoint` aponta para `http://localhost:4317` (Tempo/OTLP) e sampling está 100% por padrão (via `tracing.sampling.probability: 1.0`).

Documentação Swagger

Atualmente não há integração com Swagger/OpenAPI neste projeto. A documentação de endpoints deve ser consultada diretamente no código ou em outras fontes que você mantenha manualmente.

Métricas personalizadas

- `MetricsRecorder` registra contadores e gauges via `MeterRegistry`.
- `CriarPedidoService` registra um contador `pedido.quantidade` ao salvar um pedido.

Testes

- Há testes básicos no diretório `src/test/kotlin`. Rode os testes com:

```bash
./mvnw test
```

Notas, suposições e próximos passos

- Suponho que a intenção é permitir alternância entre o `PedidoRepositoryInMemory` e uma implementação JPA (há infraestrutura pronta para ambos). O repositório in-memory é o comportamento default; para usar JPA ative o profile `db`.
- Recomendo adicionar mensagens de validação (i18n) se quiser mensagens de erro mais amigáveis/acentuadas em PT-BR.
- Melhorias possíveis: paginação/listagem de pedidos, testes de integração, autenticação/autorização, contrato OpenAPI/Swagger gerado a partir de `api.yaml` (se você preferir manter a spec separada).

Commit

Este README foi atualizado para refletir as últimas alterações de código (validação, ControllerAdvice, 201 Created, dessativação temporária de DevTools restart em execuções de teste).
