package com.rtzan.camel.sbcr.rest.server;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.rtzan.camel.sbcr.rest.common.tls.SslParameters.SSL_CONTEXT_PARAMETERS;

@Component
@Profile({"!test", "jetty-server"})
public class JettyRestServerRouteBuilder extends RouteBuilder {

    private final String serverSchema;
    private final String serverAddress;
    private final int serverPort;

    private final String applicationVersion;

    @Autowired
    public JettyRestServerRouteBuilder(
            @Value("${server.schema:http}") String serverSchema,
            @Value("${server.address:0.0.0.0}") String serverAddress,
            @Value("${server.port:8083}") int serverPort,
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
                .component("jetty")
                .scheme(serverSchema)
                .host(serverAddress)
                .port(serverPort)
                .endpointProperty("sslContextParameters", "#" + SSL_CONTEXT_PARAMETERS)
                .bindingMode(RestBindingMode.json)
                .dataFormatProperty("prettyPrint", "true")
                .apiContextPath("/api/v1/api-doc")
                    .apiContextRouteId("api-doc")
                    .apiProperty("api.title", "User API")
                    .apiProperty("api.version", applicationVersion)
                .componentProperty("matchOnUriPrefix", "true")
        ;

        // @formatter:on
    }
}
