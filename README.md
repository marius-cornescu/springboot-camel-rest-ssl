# SpringBoot Camel Rest with SSL
A restfull jetty server and client with mutual ssl authentication

## Server Run command
`>$ java -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar`

## Urls
http://0.0.0.0:8083/api-doc
http://0.0.0.0:8083/users


### JETTY SERVER

java -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=jetty-server --spring.config.location=./src/main/resources/jetty-application.properties

#### DEBUG
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005 -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=jetty-server --spring.config.location=./src/main/resources/DEV-application.properties


#### PROBE Commands

wget --quiet --no-check-certificate --certificate=/security/certs/sbcr.as.crt --private-key=/security/certs/sbcr.as.key --method GET --timeout=0 'https://127.0.0.1:8083/' ; echo $?

wget --quiet --no-check-certificate --certificate=/security/certs/sbcr.as.crt --private-key=/security/certs/sbcr.as.key --method GET --timeout=0 'https://127.0.0.1:8083/ready/' ; echo $?

----------------------------------------------
wget -O- --no-check-certificate --certificate=/security/certs/sbcr.as.crt --private-key=/security/certs/sbcr.as.key --method GET --timeout=0 'https://127.0.0.1:8083/'

wget --quiet --no-check-certificate --certificate=/security/certs/sbcr.as.crt --private-key=/security/certs/sbcr.as.key --method GET --timeout=0 'https://127.0.0.1:8083/ready/' ; echo $?
----------------------------------------------

### NETTY SERVER

java -jar target/springb-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=netty-server --spring.config.location=./src/main/resources/netty-application.properties



## Build docker image

docker build -f .\.docker\dockerfile --rm -t artizan.org/spring-camel:1.0 .

docker container rm sbr-ssl
docker run --name sbr-ssl -p 18081:8081 -p 18083:8083 -it artizan.org/spring-camel:1.0 sh

----------------------------------------------
`docker build --rm --build-arg http_proxy=$http_proxy -t artizan.org/spring-camel:1.0 .`
----------------------------------------------
Publish or expose port (-p, --expose)
$ docker run -p 127.0.0.1:80:8080/tcp ubuntu bash
This binds port 8080 of the container to TCP port 80 on 127.0.0.1 of the host machine. You can also specify udp and sctp ports.
----------------------------------------------

## Deploy in k8s cluster
1. Run `kubectl create -f k8s-camel-spring.deployment.yml`
2. Run `kubectl get pods` to see the pod.
3. Run `k exec [k8s-camel-spring] -it sh` to shell into the container. Type `exit` to exit the shell.



## TESTS
//================================================================================================================
### Test with cURL for PROBE - FAILED mTLS

curl --pubkey ~/.ssh/id_rsa.pub

curl -k --tlsv1.2 --key sbcr-server/src/main/resources/certs/java-project.as.key --key-type PEM --pubkey sbcr-server/src/main/resources/certs/java-project.as.crt --location --request GET 'https://127.0.0.1:8083/ready/'

curl --key /cygdrive/c/cygwin64/home/mcornescu/.ssh/id_rsa --pubkey /cygdrive/c/cygwin64/home/mcornescu/.ssh/id_rsa.pub --location --request GET 'https://127.0.0.1:8083/ready/'


curl -k --tlsv1.2 --key sbcr-server/src/main/resources/certs/java-project.as.key --key-type PEM --pubkey sbcr-server/src/main/resources/certs/java-project.as.pub --location --request GET 'https://127.0.0.1:8083/ready/'

curl -k --tlsv1.2 --key sbcr-server/src/main/resources/certs/java-project.as --pubkey sbcr-server/src/main/resources/certs/java-project.as.pub --location --request GET 'https://127.0.0.1:8083/ready/'


curl -k --cert sbcr-server/src/main/resources/certs/java-project.as.crt --key sbcr-server/src/main/resources/certs/java-project.as.key.pem --location --request GET 'https://127.0.0.1:8083/ready/'
//================================================================================================================
### Test with WGET for PROBE

wget --no-check-certificate --ca-cert=artizan_solutions_CA.crt --certificate=java-project.as.crt --private-key=java-project.as.key --method GET --timeout=0 'https://127.0.0.1:8083/ready/'

wget -O- --no-check-certificate --certificate=java-project.as.crt --private-key=java-project.as.key --method GET --timeout=0 'https://127.0.0.1:8083/ready/'

wget --quiet --no-check-certificate --certificate=java-project.as.crt --private-key=java-project.as.key --method GET --timeout=0 'https://127.0.0.1:8083/ready/' ; echo $?

simulate FAILURE:
wget --quiet --no-check-certificate --certificate=cert.pem --private-key=key.pem --method GET --timeout=0 'https://127.0.0.1:8083/ready/' ; echo $?

//================================================================================================================





[https://alpine.pkgs.org/3.12/alpine-community-aarch64/openjdk11-jre-headless-11.0.7_p10-r1.apk.html]











//================================================================================================================
## References

1) https://www.codeproject.com/articles/326574/an-introduction-to-mutual-ssl-authentication
2) https://stackoverflow.com/questions/9573894/set-up-netty-with-2-way-ssl-handsake-client-and-server-certificate

