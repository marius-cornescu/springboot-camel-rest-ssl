# SpringBoot Camel Rest with SSL
A restfull jetty server and client with mutual ssl authentication

## Server Run command
`springboot-camel-rest-ssl/server$ java -jar target/springboot-camel-rest-server-1.0-SNAPSHOT.jar`

## Urls
http://0.0.0.0:8080/api-doc

http://0.0.0.0:8080/users


### JETTY SERVER

java -jar target/springboot-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=jetty-server --spring.config.location=classpath:/jetty-application.properties

### NETTY SERVER

java -jar target/springboot-camel-rest-server-1.0-SNAPSHOT.jar -Dspring.profiles.active=netty-server --spring.config.location=classpath:/netty-application.properties

jetty-application.properties

-Dspring.profiles.active=netty-server
-Dspring.profiles.active=jetty-server

--spring.config.location=classpath:/another-location.properties

## References

1) https://www.codeproject.com/articles/326574/an-introduction-to-mutual-ssl-authentication
2) https://stackoverflow.com/questions/9573894/set-up-netty-with-2-way-ssl-handsake-client-and-server-certificate

