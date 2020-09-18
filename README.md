# SpringBoot Camel Rest with SSL
A restfull jetty server and client with mutual ssl authentication

## Server Run command
`>$ java -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar`

## Urls
http://0.0.0.0:8080/api-doc
http://0.0.0.0:8080/users


### JETTY SERVER

java -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=jetty-server --spring.config.location=./src/main/resources/jetty-application.properties

java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=jetty-server --spring.config.location=./src/main/resources/jetty-application.properties

### NETTY SERVER

java -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=netty-server --spring.config.location=./src/main/resources/netty-application.properties



## Build docker image
`docker build --rm -t artizan.org/spring-camel:1.0 .`

`docker build --rm --build-arg http_proxy=$http_proxy -t artizan.org/spring-camel:1.0 .`

`docker run -it artizan.org/spring-camel:1.0 sh`

## Deploy in k8s cluster
1. Run `kubectl create -f k8s-camel-spring.deployment.yml`
2. Run `kubectl get pods` to see the pod.
3. Run `k exec [k8s-camel-spring] -it sh` to shell into the container. Type `exit` to exit the shell.













[https://alpine.pkgs.org/3.12/alpine-community-aarch64/openjdk11-jre-headless-11.0.7_p10-r1.apk.html]











//================================================================================================================
## References

1) https://www.codeproject.com/articles/326574/an-introduction-to-mutual-ssl-authentication
2) https://stackoverflow.com/questions/9573894/set-up-netty-with-2-way-ssl-handsake-client-and-server-certificate

