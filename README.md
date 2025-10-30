# ms-order-api

Resumo rápido

Microserviço em Kotlin com Spring Boot para gerenciamento de pedidos (orders). Implementa endpoints REST para criação e consulta de pedidos, métricas com Micrometer/Prometheus e integração opcional com PostgreSQL via JPA/Flyway. O projeto segue um estilo Ports & Adapters (Hexagonal) claro: casos de uso na camada de aplicação, lógica de negócio no domínio e adaptadores para entrada (REST) e saída (repositórios, métricas).

Checklist do que este README cobre

- [x] O que o projeto faz
- [x] Arquitetura e organização do código
- [x] Tecnologias usadas
- [x] Endpoints e exemplos de payload
- [x] Como rodar (modo rápido com repositório em memória / com PostgreSQL)
- [x] Docker / docker-compose / Kubernetes (Makefile)
- [x] Observabilidade (Actuator, Prometheus, OpenTelemetry)
- [x] Migrações (Flyway)
- [x] Testes e próximos passos

Visão geral - o que o projeto faz

O serviço expõe uma API REST com os principais recursos para gerenciar pedidos:

- Criar pedido: POST /orders
- Consultar pedido: GET /orders/{id}

Ao criar um pedido são calculados os totais a partir dos itens informados e o pedido é persistido (por padrão, há um repositório em memória ativo via `BeansConfiguration`). Há também implementações para persistência via JPA (entidades em `infrastructure/adapter/output/repository/entity`) e um `PedidoRepositoryImpl` que usa `PedidoJPARepository`.

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
- OpenTelemetry (OTLP exporter) / Tempo (configurada nos exemplos)
- Docker (Dockerfile) e Docker Compose (em `docker-local/`)
- Kubernetes manifests em `k8s/` e Makefile para deploy local

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
curl -s -X POST http://localhost:8080/orders \
  -H 'Content-Type: application/json' \
  -d '{"cliente": {"nome":"João","email":"joao@example.com","cpf":"000"},"enderecoEntrega":{"rua":"Rua","numero":"1","complemento":null,"bairro":"B","cidade":"C","estado":"S","cep":"00000-000"},"itens":[{"produto":"X","quantidade":1,"precoUnitario":10.0}] }'
```

- Resposta: HTTP 200 com payload do pedido criado (campos: id, dataCriacao, cliente, enderecoEntrega, itens, valorTotal, status).

2) Consultar pedido

- URL: GET /orders/{id}
- Exemplo de curl:

```bash
curl -s http://localhost:8080/orders/1
```

- Resposta: HTTP 200 com o pedido ou 404 se não encontrado.

Persistência e migrações

- Há arquivos de migração SQL localizados em `src/main/resources/db/migration` (`V1__...` até `V5__...`).
- `DataSourceConfig` define datasources `spring.datasource.write` (primary) e `spring.datasource.read` e cria um bean `Flyway` que usaria o datasource de escrita.
- Atenção: no `application.yml` as propriedades do Flyway estão com `enabled: false` — ajuste se quiser ativar as migrações automaticamente.
- Por padrão (conforme `BeansConfiguration`), a aplicação inicia com um `PedidoRepositoryInMemory`. Para usar JPA, é necessário trocar o bean ou criar uma configuração que exponha `PedidoRepositoryImpl` com um `PedidoJPARepository` real.

Executando o projeto

Modo rápido (sem banco)

1) Build e run com Maven (usa repositório em memória configurado nos beans):

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

2) Ajuste `src/main/resources/application.yml` ou exporte variáveis de ambiente para apontar `spring.datasource.write` e `spring.datasource.read` para os containers Postgres (ex.: `jdbc:postgresql://localhost:5432/order_db`).

3) Habilite Flyway no `application.yml` (set `spring.flyway.enabled: true`) se quiser aplicar migrações automaticamente.

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
- Micrometer + Prometheus: há dependência de `micrometer-registry-prometheus` e o `docker-local/docker-compose.yml` inclui um container Prometheus; as métricas do app são expostas em `/actuator/prometheus`.
- OpenTelemetry: `otel.exporter.otlp.endpoint` aponta para `http://localhost:4317` (Tempo/OTLP) e sampling está 100% por padrão (via `tracing.sampling.probability: 1.0`).

Métricas personalizadas

- `MetricsRecorder` registra contadores e gauges via `MeterRegistry`.
- `CriarPedidoService` registra um contador `pedido.quantidade` ao salvar um pedido.

Testes

- Há testes básicos no diretório `src/test/kotlin`. Rode os testes com:

```bash
./mvnw test
```

Notas, suposições e próximos passos

- Suponho que a intenção é permitir alternância entre o `PedidoRepositoryInMemory` e uma implementação JPA (há infraestrutura pronta para ambos). O bean default atual expõe `PedidoRepositoryInMemory` no `BeansConfiguration`.
- Se for rodar em produção com Postgres, ative Flyway e a configuração de datasources adequadas e remova/ajuste o bean in-memory.
- Melhorias possíveis: validação de payload (Bean Validation), DTOs mais ricos com IDs gerados, paginação/listagem de pedidos, testes de integração, autenticação/autorização, contrato OpenAPI/Swagger.

Contato / Como contribuir

- Abra issues/PRs no repositório com descrições claras. Para alterações comportamentais (por exemplo, ativar JPA por padrão), inclua testes.

Licença

- Não definida no projeto (arquivo de licença não presente). Adicione um `LICENSE` se desejar explicitar a licença.


---

Se quiser, eu posso:

- Gerar exemplos de testes de integração (SpringBootTest) que inicializam o contexto e testam os endpoints.
- Alterar `BeansConfiguration` para trocar para implementação JPA por profile (`dev`/`prod`).
- Adicionar um `docker-compose` menor só com Postgres + aplicação para facilitar desenvolvimento.

Me diga qual desses próximos passos prefere que eu faça e eu implemento.
