package com.rtzan.camel.sbcr.rest.server;

import com.rtzan.camel.sbcr.server.model.ServiceFullStatus;
import com.rtzan.camel.sbcr.server.model.ServiceReadinessStatus;
import com.rtzan.camel.sbcr.server.model.ServiceStatus;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.rtzan.camel.sbcr.rest.common.ApplicationProperties.APP_NAME;
import static com.rtzan.camel.sbcr.rest.common.tls.SslParameters.SSL_CONTEXT_PARAMETERS;

@Component
@Profile({"!test", "jetty-server"})
public class JettyRestServerRouteBuilder extends RouteBuilder {

    public static final String ROUTE_ID_STATUS_GET = APP_NAME + ".admin.status";
    public static final String ROUTE_ID_READY_GET = APP_NAME + ".admin.ready";

    private final String serverSchema;
    private final String serverAddress;
    private final int serverPort;

    private final String applicationVersion;
    private final ServiceFullStatus serviceFullStatus;
    private final ServiceReadinessStatus serviceReadinessStatus;

    @Autowired
    public JettyRestServerRouteBuilder(
            @Value("${server.schema:http}") String serverSchema,
            @Value("${server.address:0.0.0.0}") String serverAddress,
            @Value("${server.port:8083}") int serverPort,
            @Value("${application.version}") String applicationVersion,
            ServiceFullStatus serviceFullStatus,
            ServiceReadinessStatus serviceReadinessStatus
    ) {
        this.serverSchema = serverSchema;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        this.applicationVersion = applicationVersion;
        this.serviceFullStatus = serviceFullStatus;
        this.serviceReadinessStatus = serviceReadinessStatus;
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

        rest("/").description("Service Status")
                .consumes("application/json")
                .produces("application/json")

                .get().id(ROUTE_ID_STATUS_GET).description("Server status").outType(ServiceStatus.class)
                .responseMessage().code(200).message("Server is online").endResponseMessage()
                .route().setBody(constant(serviceFullStatus));

        rest("/ready").description("Service Ready")
                .consumes("application/json")
                .produces("application/json")

                .get().id(ROUTE_ID_READY_GET).description("Server status").outType(ServiceStatus.class)
                .responseMessage().code(200).message("Server is online").endResponseMessage()
                .route().setBody(constant(serviceReadinessStatus));

        // @formatter:on
    }

    @Bean
    public static ServiceFullStatus newServiceStatus(
            @Value("${application.version}") String applicationVersion,
            @Value("${application.mode}") String applicationMode
    ) {
        return new ServiceFullStatus("springboot-camel-rest-ssl", applicationVersion, applicationMode, "OK");
    }

    @Bean
    public static ServiceReadinessStatus newServiceReadinessStatus(
            @Value("${application.version}") String applicationVersion,
            @Value("${application.mode}") String applicationMode
    ) {
        return new ServiceReadinessStatus("springboot-camel-rest-ssl", applicationVersion, applicationMode, "OK");
    }
}
