package com.rtzan.camel.sbcr.rest.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = {
        "com.rtzan.camel.sbcr.rest.server",
        "com.rtzan.camel.sbcr.server.model",
})
public class ServerApplication {

    /**
     * Main method to start the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
