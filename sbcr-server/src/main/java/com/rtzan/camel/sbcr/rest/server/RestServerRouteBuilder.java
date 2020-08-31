package com.rtzan.camel.sbcr.rest.server;

import com.rtzan.camel.sbcr.server.model.UserEntity;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static org.apache.camel.model.rest.RestParamType.body;
import static org.apache.camel.model.rest.RestParamType.path;

@Component
public class RestServerRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // @formatter:off
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
