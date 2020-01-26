package com.rtzan.camel.springboot.rest.server;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * A simple Camel REST DSL route with Swagger API documentation.
 *
 */
@Component
@Profile({"!test", "netty-server"})
public class NettyRestServerRouteBuilder extends RouteBuilder {

    private final String serverSchema;
    private final String serverAddress;
    private final int serverPort;

    private final String applicationVersion;

    @Autowired
    public NettyRestServerRouteBuilder(
            @Value("${server.schema:http}") String serverSchema,
            @Value("${server.address:0.0.0.0}") String serverAddress,
            @Value("${server.port:8084}") int serverPort,
            @Value("${application.version}") String applicationVersion
    ) {
        this.serverSchema = serverSchema;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        this.applicationVersion = applicationVersion;
    }

    @Override
    public void configure() {

        // @formatter:off
        restConfiguration()
                .component("netty-http")
                .scheme(serverSchema)
                .host(serverAddress)
                .port(serverPort)
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .apiContextPath("/api/v1/api-doc")
                    .apiContextRouteId("api-doc")
                    .apiProperty("api.title", "User API")
                    .apiProperty("api.version", applicationVersion)
                    .apiProperty("cors", "true")
                .componentProperty("matchOnUriPrefix", "true")
        ;

        // @formatter:on
    }
}
