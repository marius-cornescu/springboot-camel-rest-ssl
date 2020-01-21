package com.rtzan.camel.springboot.rest.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {
        "com.rtzan.camel.springboot.rest.server",
        "com.rtzan.camel.springboot.server.model",
})
public class ServerApplication {

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
