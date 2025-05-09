# Sistema de Pedidos Online

## Descrição do Projeto
Este projeto é um sistema de pedidos online desenvolvido em **Kotlin** e **Spring Boot**, seguindo os princípios da **Arquitetura Hexagonal**. O objetivo principal é gerenciar pedidos, clientes e endereços, permitindo a integração futura com APIs externas, como serviços de pagamento e controle de estoque.

## Funcionalidades
- Cadastro de clientes com informações básicas (nome, e-mail, CPF).
- Registro de endereços associados aos clientes.
- Gerenciamento de itens de pedidos, incluindo produto, quantidade e preço unitário.
- Estrutura preparada para integração com serviços externos.

## Estrutura do Projeto
A estrutura do projeto foi organizada para seguir os princípios da Arquitetura Hexagonal, separando o núcleo do domínio das dependências externas. Abaixo está a estrutura atual:

src/ ├── main/ │ 
     ├── kotlin/ │ 
     │ └── com/ │ 
     │ └── order/ │ 
     │ └── api/ │ │ ├── domain/ │ │ │ └── model/ │ │ │ ├── Cliente.kt │ │ │ ├── Endereco.kt │ │ │ └── ItemPedido.kt │ │ └── application/ │ │ └── (futuras implementações de casos de uso) │ └── resources/ │ └── application.yml └── test/ └── (testes automatizados)

### Detalhes das Camadas
1. **Domain (Domínio)**:
    - Contém as entidades principais do sistema (`Cliente`, `Endereco`, `ItemPedido`).
    - Isolado de dependências externas, garantindo independência do núcleo.

2. **Application (Aplicação)**:
    - Camada responsável por orquestrar os casos de uso do sistema.
    - Ainda não implementada, mas será utilizada para coordenar as interações entre o domínio e as portas.

3. **Infraestrutura**:
    - Camada que será utilizada para implementar adaptadores para APIs externas (pagamentos, estoque) no futuro.

## Tecnologias Utilizadas
- **Kotlin**: Linguagem principal para o desenvolvimento.
- **Spring Boot**: Framework para criação de aplicações robustas e escaláveis.
- **Maven**: Gerenciador de dependências e build.

## Futuras Implementações
- Integração com uma API de pagamentos para processar transações.
- Integração com uma API de estoque para verificar a disponibilidade de produtos.
- Criação de portas (interfaces) e adaptadores para comunicação com serviços externos.

## Como Executar o Projeto
1. Certifique-se de ter o **Java 17** e o **Maven** instalados.
2. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO># ms-order-api
