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

## Implementation documentation
[//]: # (TODO: write documentation after implementing)

## Deployment

To deploy locally you will need:
- RabbitMQ (tested version `3.12.10`)
- PostgresSQL (tested version `16.2`)

To deploy via docker compose you will need `docker` and `docker-compose` installed.

### Local deployment

1. Launch PostgresSQL.  Prepare local database
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