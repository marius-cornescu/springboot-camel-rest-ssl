package com.rtzan.camel.springboot.rest.server;


import com.rtzan.camel.springboot.server.model.UserEntity;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

/**
 * A simple Camel REST DSL route with Swagger API documentation.
 *
 */
@Component
public class ServerCamelRouter extends RouteBuilder {

    @Value("${server.schema:http}")
    private String serverSchema;
    @Value("${server.address:0.0.0.0}")
    private String serverAddress;
    @Value("${server.port:8084}")
    private int serverPort;

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
            .apiContextPath("/api-doc")
                .apiProperty("api.title", "User API").apiProperty("api.version", "1.0.0")
                .apiProperty("cors", "true");


        rest("/users").description("User REST service")
            .consumes("application/json")
            .produces("application/json")

            .get().description("Find all users").outType(UserEntity[].class)
                .responseMessage().code(200).message("All users successfully returned").endResponseMessage()
                .to("bean:userService?method=findUsers")

            .get("/{id}").description("Find user by ID")
                .outType(UserEntity.class)
                .param().name("id").type(path).description("The ID of the user").dataType("integer").endParam()
                .responseMessage().code(200).message("User successfully returned").endResponseMessage()
                .to("bean:userService?method=findUser(${header.id})")

            .put("/{id}").description("Update a user").type(UserEntity.class)
                .param().name("id").type(path).description("The ID of the user to update").dataType("integer").endParam()
                .param().name("body").type(body).description("The user to update").endParam()
                .responseMessage().code(204).message("User successfully updated").endResponseMessage()
                .to("bean:userService?method=updateUser");

        // @formatter:on
    }

}
