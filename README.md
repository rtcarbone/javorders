# JavOrders

JavOrders é um sistema distribuído para gerenciamento de pedidos, desenvolvido com arquitetura de microsserviços e
orientado a mensagens usando RabbitMQ. O projeto segue os princípios da Clean Architecture para garantir separação de
responsabilidades, testabilidade e manutenção escalável.

## 🧱 Arquitetura

O sistema é dividido nos seguintes microsserviços:

- **cliente-service**: Gerencia informações dos clientes.
- **produto-service**: Gerencia produtos e controle de estoque.
- **estoque-service**: Responsável por baixar o estoque dos produtos durante o processamento do pedido.
- **pedido-service**: Responsável por criar e orquestrar pedidos.
- **pagamento-service**: Gerencia o processo de pagamento de pedidos.
- **pedido-receiver**: Serviço que consome mensagens de novos pedidos para iniciar os fluxos de estoque e pagamento.

Cada serviço implementa:

- Clean Architecture (Camadas: domain, application, infrastructure, config)
- Comunicação assíncrona via RabbitMQ
- Repositórios com Spring Data JPA (PostgreSQL)
- Lombok para reduzir boilerplate

## 📦 Tecnologias Utilizadas

- Java 21
- Spring Boot 3
- RabbitMQ
- PostgreSQL
- Docker e Docker Compose
- Maven
- JUnit e Mockito (testes unitários e de componente)

## 🔄 Fluxo de um novo pedido

1. O pedido é criado com status `ABERTO`.
2. O pedido é enviado via RabbitMQ para a fila `novo-pedido-queue`.
3. O `pedido-receiver` consome a mensagem:
    - Valida e consulta os dados de cliente e produto.
    - Solicita baixa de estoque ao `produto-service`.
    - Solicita o pagamento ao `pagamento-service`.
4. O `pedido-service` atualiza o status do pedido:
    - `FECHADO_COM_SUCESSO`, `FECHADO_SEM_ESTOQUE` ou `FECHADO_SEM_CREDITO`.

## 🐳 Executando com Docker

Certifique-se de ter o Docker e Docker Compose instalados.

```bash
docker-compose up --build