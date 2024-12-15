# order-api

## Objetivo do Serviço
Gerenciar e calcular os produtos do pedido.

## Fluxo Detalhado
1 - Produto Externo A → Kafka (Order Topic)

O Produto Externo A produz mensagens contendo os detalhes do pedido e as envia para o tópico Order Topic no Kafka.

### Exemplo de mensagem enviada

```
POST 

{
  "externalId": "43eae40e-b34a-48d1-9b0d-345b00a9169c",
  "totalValue": 10.0,
  "status": "PENDING",
  "items": [
    { "name": "Produto 1", "quantity": 1, "price": 30.0 },
    { "name": "Produto 2", "quantity": 2, "price": 70.0 }
  ]
}

``` 

2 - Kafka (Order Topic) → Order Service

O Order Service consome a mensagem do tópico Order Topic.
Processamento realizado pelo OrderService:
Calcula o valor total dos itens.
Salva o pedido no banco de dados Orders.
Publica o pedido processado no tópico orders-calculated Topic.
Exemplo do pedido calculado publicado:

```
{
   "orderId": 1,
   "status": "PENDING",
   "totalValue": 100.0,
   "createdAt": "2024-12-15T11:04:23"
}
```

Kafka (orders-calculated Topic) → Calculated Orders Database

O consumidor do orders-calculated Topic lê as mensagens e salva os pedidos processados no banco de dados Calculated Orders.
O armazenamento final garante que apenas pedidos já processados sejam salvos.

## Migrations
> Para nomeclatura da criação das migrations, deve-se utilizar V + 1 e uma descrição daquele migration como segue abaixo:

| Scripts                   | 
|---------------------------|
| V01__create_order_table   |
| V02__create_calculated_orders.sql |

## Tecnologias utilizadas
- Java 17
- SpringBoot 3
- Maven 3
- Docker
- Docker compose
- Postgres
- Flyway
- Kafka

## Executando os testes unitários

Para executar os testes unitários, acesse a pasta raiz do projeto e execute o comando a baixo.

    mvn clean test

## Local com Maven e Spring Boot
Para executar a aplicação execute o comando a baixo.

    mvn spring-boot:run -DskipIntegrationTests

Para facilitar a montagem do ambiente local de desenvolvimento, o projeto já vem acompanhado
de um arquivo `docker-compose.yaml`, que sobe em contêineres `Docker` toda a pilha utilizada
pelo projeto. E necessário estar instalado na máquina o `Docker` e o `docker-compose`.

Para subir a pilha, execute o seguinte comando a partir da pasta raíz do projeto:
```docker
docker-compose up -d
```

Para encerrar a execução dos serviços, execute:
```docker
docker-compose down
```

## Como executar um container do projeto?
Caso seja necessário publicar API de forma local, iremos utilizar Docker para isso. Mas antes, é necessário executar as etapas abaixo:

Para executar o compose-dev deve utilizar o seguinte comando:
```docker
docker-compose -f docker-compose.dev.yml up -d
```

Para atualizar com as novas mudanças da repositório, execute o comando para derrubar seu compose
```docker
docker-compose -f docker-compose.dev.yml down
```

# Swagger
http://localhost:8080/swagger-ui.html

### Exemplo de request para processar ordem

```
POST /api/orders/process

{
  "externalId": "43eae40e-b34a-48d1-9b0d-345b00a9169c",
  "totalValue": 10.0,
  "status": "PENDING",
  "items": [
    { "name": "Produto 1", "quantity": 1, "price": 30.0 },
    { "name": "Produto 2", "quantity": 2, "price": 70.0 }
  ]
}

```

Design System 
![orders](https://github.com/user-attachments/assets/5cd9b181-5301-4b53-8594-45e296550380)


