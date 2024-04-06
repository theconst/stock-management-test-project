# Sales microservice

Microservice for sales of products and customer relationship.

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
../gradlew :sales:buildDockerImage
```
Then use the corresponding docker run command, mapping port `8081`, e. g.:
```shell
docker run -p 8081:8081 purchases:1.0-SNAPSHOT
```