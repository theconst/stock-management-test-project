# eCLUB Paraguay test task

## Task description
### Requisites
#### Microservice for the product purchases
- [] REST API for the creation of products.
- [] REST API for the product purchases.
- [] Use a message queue (RabbitMQ) to sum the purchased products to the stock available in the warehouse.
#### Microservices for the product sales
- [] Implement a REST API for creating clients.
- [] Implement a REST API for the sale of products.
- [] Use a message queue (RabbitMQ) to subtract the products sold from the stock available in the warehouse.
#### Shared database
- [] Both microservices must use the same database to manage the product information.
#### Warehouse operation:
- [] Stock update operations must be performed asynchronously through the queue messages (RabbitMQ). 
There is only one warehouse or warehouse for all the products. 

### Expected output
- [] Source code
- [] Deployment docs
- [] Unit tests

## Implementation documentation
[//]: # (TODO: write documentation after implementing)