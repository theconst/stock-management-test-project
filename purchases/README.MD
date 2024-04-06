# Purchases microservice

Microservice for addToStock of products and product management

## Launching locally

### Using gradle run.
```shell
export RABBIT_HOST='localhost'
export DB=eclub
export DB_USER=eclub
export DB_PASSWORD='<password>'
export DB_HOST='localhost' 

../gradlew :purchases :run
```
### Using docker image
```shell
../gradlew :purchases:buildDockerImage
```
Then use the corresponding docker run command, mapping port `8080`, e. g.:
```shell
docker run -p 8080:8080 purchases:1.0-SNAPSHOT
```