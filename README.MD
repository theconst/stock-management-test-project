# eCLUB Paraguay test task

## Task description
_This is just a description of the task as state in the presentation, feel free to skip the section_

### Requisites
#### Microservice for the product purchases
- [x] REST API for the creation of products.
- [x] REST API for the product purchases.
- [x] Use a message queue (RabbitMQ) to add the purchased products to the stock available in the warehouse.
#### Microservices for the product sales
- [x] Implement a REST API for creating clients (customers).
- [x] Implement a REST API for the sale of products.
- [x] Use a message queue (RabbitMQ) to subtract the products sold from the stock available in the warehouse.
#### Shared database
- [x] Both microservices must use the same database to manage the product information.
#### Warehouse operation:
- [x] Stock update operations must be performed asynchronously through the queue messages (RabbitMQ). 
There is only one warehouse or warehouse for all the products. 

### Expected output
- [x] Source code
- [x] Deployment docs
- [x] Unit test

## Implementation
The application consists of 2 services:
- `purchases`. Repsonsible for adding products and stock operations.
- `sales`. Responsible for handling sales and customers.

Each service exposes REST API to manage the respectful entities.
Basic API docs are accessible via `/swagger-ui.html` endpoints.

Each service uses a shared PostgresSQL database.
Shared schema is managed by `migration` module using [flyway](https://flywaydb.org/).

Operation that involve stock operation are performed asynchronously using `RabbitMQ` as message broker.

`Spring Boot` is a backbone of the application. 
Additionally, [lombok]() and [mapstruct]() as utility libraries to generate boilerplate code.

`Spring WebFlux` and `Netty` are responsible for the web layer of the application.

`Spring data` and `r2dbc` driver handle access to the database.

Some simplification are made in order to make the codebase more concise for demonstration purposes, e. g.:
- Access management to DB and message queue is not separated per service to not accumulate administration code.
- System does not allow for all plausible workflows (e. g. sale returns are not implemented directly), even though
the code can be extended to do it.
- Error handling of async error is not automated. For example, when the sale of stock item fails manual intervention is 
required to handle message in the dead letter queue and restore the integrity of data.

## Usage

Basic workflow consists of:
1. Creating products via `<purchases-url>/products` products endpoint
2. Purchasing products via `<pourchases-url>/purchases` endpoint
(*Note*: you can check products via `<purchases-url>/stock-items` endpoint, but purchases are not recorded).
Products are purchased in batches and you can add  
3. Add customer information to via `<sales-url>/customers` endpoint.
4. When there are items and customers the `stock items`, they can be sold via `<sales-url>/sales` endpoint. 

Stock operations are performed asynchronously.
After performing `sale` or `purchase` the `stockOperationId` is returned and its status can be checked via
`<purchases-url>/stock-items/operations/{id}/status` endpoint.

## Deployment

To deploy locally you will need:
- RabbitMQ (tested version `3.12.10`)
- PostgresSQL (tested version `16.2`)

To deploy via docker compose you will need `docker` and `docker-compose` installed.

### Local deployment

1. Launch PostgresSQL. Prepare local database
    ```sql
    CREATE USER eclub;
    GRANT CREATE ON SCHEMA public TO eclub;
    GRANT ALL PRIVILEGES ON DATABASE "eclub" to eclub;
    ```
2. Setup [migration](migration/README.MD) and launch the migration to prepare database.
3. Verify RabbitMQ is running. In this project default guest access is used.
4. Launch [sales](sales/README.MD). Do not forget to change the port with `-Dserver.port=8081`
5. Launch [purchases](purchases/README.MD). 

There are run configurations for IntelliJ Idea in `.run` defined for convenience.

### Deployment using `docker-compose`

Build docker images and launch docker-compose in the `docker-compose` directory, for example:
```shell
./gradlew buildDockerImage && cd docker-compose && docker-compose up
```