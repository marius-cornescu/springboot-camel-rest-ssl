package com.rtzan.camel.springboot.server.model;

import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.camel.support.jsse.TrustManagersParameters;

public class CnCheckTrustManagersParameters extends TrustManagersParameters {

    private String acceptedCNsString;

    public CnCheckTrustManagersParameters(String acceptedCNsString) {
        this.acceptedCNsString = acceptedCNsString;
    }

    @Override
    public TrustManager[] createTrustManagers() throws GeneralSecurityException, IOException {
        return new TrustManager[]{new CnCheckTrustManager(acceptedCNsString, super.createTrustManagers())};
    }

}
