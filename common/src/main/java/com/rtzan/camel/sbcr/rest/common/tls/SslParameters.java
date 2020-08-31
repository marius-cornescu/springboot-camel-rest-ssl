package com.rtzan.camel.sbcr.rest.common.tls;

import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.SSLContextServerParameters;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SslParameters {

    public static final String SSL_CONTEXT_PARAMETERS = "sslContextParameters";
    public static final String TLS_CONTEXT_PARAMETERS = "tlsContextParameters";

    private static final Logger LOGGER = LoggerFactory.getLogger(SslParameters.class);

    private final SSLContextParameters sslContextParameters;
    private final TLSClientParameters tlsContextParameters;

    @Autowired
    public SslParameters(
            @Value("${ssl.client.authentication}") String clientAuthentication,
            SslManagersParameters sslManagersParameters
    ) {
        this.sslContextParameters = createSSLContextParameters(clientAuthentication, sslManagersParameters);
        this.tlsContextParameters = createTLSContextParameters(sslManagersParameters);
    }

    @Bean(name = SSL_CONTEXT_PARAMETERS)
    public SSLContextParameters getSSLContextParameters() {
        return this.sslContextParameters;
    }

    @Bean(name = TLS_CONTEXT_PARAMETERS)
    public TLSClientParameters getTLSContextParameters() {
        return this.tlsContextParameters;
    }

    private static SSLContextParameters createSSLContextParameters(String clientAuthentication, SslManagersParameters sslManagersParameters) {
        try {
            SSLContextServerParameters serverParameters = createClientAuthenticationParameter(clientAuthentication);

            SSLContextParameters sslParameters = new SSLContextParameters();
            sslParameters.setKeyManagers(sslManagersParameters.getKeyManagersParameters());
            sslParameters.setTrustManagers(sslManagersParameters.getTrustManagersParameters());
            sslParameters.setServerParameters(serverParameters);

            return sslParameters;
        } catch (Exception e) {
            LOGGER.info("Failed to create SSLContextParameters because: ", e);
            return null;
        }
    }

    private static SSLContextServerParameters createClientAuthenticationParameter(String clientAuthentication) {
        SSLContextServerParameters serverParameters = new SSLContextServerParameters();
        serverParameters.setClientAuthentication(clientAuthentication);
        return serverParameters;
    }

    private static TLSClientParameters createTLSContextParameters(SslManagersParameters sslManagersParameters) {
        try {
            TLSClientParameters sslParameters = new TLSClientParameters();
            sslParameters.setKeyManagers(sslManagersParameters.getKeyManagersParameters().createKeyManagers());
            sslParameters.setTrustManagers(sslManagersParameters.getTrustManagersParameters().createTrustManagers());

            return sslParameters;
        } catch (Exception e) {
            LOGGER.warn("Failed to create TLSClientParameters because: ", e);
            return null;
        }
    }
}