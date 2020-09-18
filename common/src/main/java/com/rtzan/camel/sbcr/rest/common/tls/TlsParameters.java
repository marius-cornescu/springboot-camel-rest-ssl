package com.rtzan.camel.sbcr.rest.common.tls;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Lazy
@Component
public class TlsParameters {

    public static final String TLS_CONTEXT_PARAMETERS = "tlsContextParameters";

    private static final Logger LOGGER = LoggerFactory.getLogger(TlsParameters.class);

    private final TLSClientParameters tlsContextParameters = null;

    @Autowired
    public TlsParameters(
            @Value("${ssl.client.authentication}") String clientAuthentication,
            SslManagersParameters sslManagersParameters
    ) {
        //this.tlsContextParameters = createCxfTlsContextParameters(sslManagersParameters);
    }

    @Lazy
    @Bean(name = TLS_CONTEXT_PARAMETERS)
    public TLSClientParameters getTLSContextParameters() {
        return this.tlsContextParameters;
    }

    private static TLSClientParameters createCxfTlsContextParameters(SslManagersParameters sslManagersParameters) {
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
