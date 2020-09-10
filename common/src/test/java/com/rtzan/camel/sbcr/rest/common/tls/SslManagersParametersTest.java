package com.rtzan.camel.sbcr.rest.common.tls;

import org.apache.camel.support.jsse.KeyManagersParameters;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.jupiter.api.Assertions.*;

class SslManagersParametersTest {

    private SslManagersParameters underTest;

    @Test
    void getKeyManagersParameters() {
        // given
        String keystoreLocation = "ssl/server_keystore.pkcs12";
        String keystorePassword = "password";
        String truststoreLocation = "ssl/server_truststore.pkcs12";
        String truststorePassword = "password";
        String acceptedCNsString = "";

        // when
        underTest = new SslManagersParameters(keystoreLocation, keystorePassword, truststoreLocation, truststorePassword, acceptedCNsString);
        KeyManagersParameters actual = underTest.getKeyManagersParameters();

        // then
        assertNotNull(actual);
    }

    @Test
    void getTrustManagersParameters() {
    }
}