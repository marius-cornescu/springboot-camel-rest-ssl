package com.rtzan.camel.sbcr.rest.common.tls;

import java.util.Objects;

import org.apache.camel.support.jsse.KeyManagersParameters;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SslManagersParameters {

    private static final Logger LOGGER = LoggerFactory.getLogger(SslManagersParameters.class);

    private final KeyManagersParameters keyManagersParameters;
    private final TrustManagersParameters trustManagersParameters;

    @Autowired
    public SslManagersParameters(@Value("${ssl.keystore.location}") String keystoreLocation,
                                 @Value("${ssl.keystore.password}") String keystorePassword,
                                 @Value("${ssl.truststore.location}") String truststoreLocation,
                                 @Value("${ssl.truststore.password}") String truststorePassword,
                                 @Value("${ssl.accepted.cns:}") String acceptedCNsString
    ) {
        this.keyManagersParameters = createKeyManagersParameters(keystoreLocation, keystorePassword);
        this.trustManagersParameters = createTrustManagersParameters(truststoreLocation, truststorePassword, acceptedCNsString);
    }

    public KeyManagersParameters getKeyManagersParameters() {
        return keyManagersParameters;
    }

    public TrustManagersParameters getTrustManagersParameters() {
        return trustManagersParameters;
    }

    private static KeyManagersParameters createKeyManagersParameters(String keystoreLocation, String keystorePassword) {
        KeyManagersParameters keyManagersParams = new KeyManagersParameters();
        try {
            if (!keystoreLocation.isEmpty()) {
                KeyStoreParameters keyStoreParameters = new KeyStoreParameters();
                keyStoreParameters.setResource(keystoreLocation);
                keyStoreParameters.setPassword(keystorePassword);

                keyManagersParams.setKeyStore(keyStoreParameters);
                keyManagersParams.setKeyPassword(keystorePassword);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to create KeyManagersParameters because: ", e);
        }
        return keyManagersParams;
    }

    private static TrustManagersParameters createTrustManagersParameters(String truststoreLocation, String truststorePassword, String acceptedCNsString) {
        TrustManagersParameters trustManagersParams;
        if (Objects.nonNull(acceptedCNsString) && !acceptedCNsString.isEmpty()) {
            trustManagersParams = new CnCheckTrustManagersParameters(acceptedCNsString);
        } else {
            trustManagersParams = new TrustManagersParameters();
        }

        return setupTrustManagersParameters(trustManagersParams, truststoreLocation, truststorePassword);
    }

    private static TrustManagersParameters setupTrustManagersParameters(TrustManagersParameters trustManagersParams, String truststoreLocation, String truststorePassword) {
        try {
            if (!truststoreLocation.isEmpty()) {
                KeyStoreParameters trustStoreParameters = new KeyStoreParameters();
                trustStoreParameters.setResource(truststoreLocation);
                trustStoreParameters.setPassword(truststorePassword);
                trustManagersParams.setKeyStore(trustStoreParameters);
            }
        } catch (Exception e) {
            LOGGER.warn("Failed to create TrustManagersParameters because: ", e);
        }
        return trustManagersParams;
    }

}
