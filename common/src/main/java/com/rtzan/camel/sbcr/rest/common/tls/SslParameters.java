package com.rtzan.camel.sbcr.rest.common.tls;

import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.SSLContextServerParameters;
import org.apache.camel.support.jsse.SecureSocketProtocolsParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Lazy
@Component
public class SslParameters {

    public static final String SSL_CONTEXT_PARAMETERS = "sslContextParameters";

    private static final Logger LOGGER = LoggerFactory.getLogger(SslParameters.class);

    private final SSLContextParameters sslContextParameters;

    @Autowired
    public SslParameters(
            @Value("${ssl.client.authentication}") String clientAuthentication,
            @Value("${ssl.protocol}") String secureSocketProtocol, // value like "TLSv1.2"
            SslManagersParameters sslManagersParameters
    ) {
        this.sslContextParameters = createSSLContextParameters(clientAuthentication, sslManagersParameters, secureSocketProtocol);
    }

    @Lazy
    @Bean(name = SSL_CONTEXT_PARAMETERS)
    public SSLContextParameters getSSLContextParameters() {
        return this.sslContextParameters;
    }

    private static SSLContextParameters createSSLContextParameters(String clientAuthentication, SslManagersParameters sslManagersParameters, String secureSocketProtocol) {
        try {
            SSLContextServerParameters serverParameters = createClientAuthenticationParameter(clientAuthentication);

            SSLContextParameters sslParameters = new SSLContextParameters();
            sslParameters.setKeyManagers(sslManagersParameters.getKeyManagersParameters());
            sslParameters.setTrustManagers(sslManagersParameters.getTrustManagersParameters());
            sslParameters.setServerParameters(serverParameters);
            if (StringUtils.hasText(secureSocketProtocol)) {
                sslParameters.setSecureSocketProtocols(createSecureSocketProtocolsParameters(secureSocketProtocol));
            }

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

    private static SecureSocketProtocolsParameters createSecureSocketProtocolsParameters(String secureSocketProtocol) {
        SecureSocketProtocolsParameters secureSocketProtocols = new SecureSocketProtocolsParameters();
        secureSocketProtocols.getSecureSocketProtocol().add(secureSocketProtocol);
        return secureSocketProtocols;
    }
}