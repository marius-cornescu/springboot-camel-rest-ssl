package com.rtzan.camel.springboot.server.model;

import javax.naming.ldap.LdapName;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CnCheckTrustManager implements X509TrustManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Set<String> acceptedCNs;
    private final TrustManager[] trustManagers;

    private final X509Certificate[] acceptedIssuers;

    public CnCheckTrustManager(String acceptedCNsString, TrustManager[] trustManagers) {
        this(stringListToSet(acceptedCNsString), trustManagers);
    }

    public CnCheckTrustManager(Set<String> acceptedCNs, TrustManager[] trustManagers) {
        this.acceptedCNs = acceptedCNs;
        this.trustManagers = Optional.ofNullable(trustManagers).orElse(new TrustManager[0]);

        this.acceptedIssuers = Stream.of(this.trustManagers)
                .map(trustManager -> ((X509TrustManager) trustManager).getAcceptedIssuers())
                .flatMap(Stream::of)
                .toArray(X509Certificate[]::new);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String authType) {
        //do nothing
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {
        boolean hasTrustedCertificate = Stream.of(trustManagers)
                .map(trustManager -> (X509TrustManager) trustManager)
                .anyMatch(x509TrustManager -> validateServerTrusted(x509TrustManager, x509Certificates, authType));

        if (!(hasTrustedCertificate && validateCN(x509Certificates))) {
            throw new CertificateException("Failed to authenticate connection.");
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return acceptedIssuers.clone();
    }

    private boolean validateServerTrusted(X509TrustManager x509TrustManager, X509Certificate[] x509Certificates, String authType) {
        try {
            x509TrustManager.checkServerTrusted(x509Certificates, authType);
            return true;
        } catch (CertificateException e) {
            logger.warn("");
            return false;
        }
    }

    private boolean validateCN(X509Certificate[] certificates) {
        if (acceptedCNs.isEmpty()) {
            // skip validation
            return true;
        }

        if (certificates != null && certificates.length > 0) {
            return Stream.of(certificates)
                    .map(cert -> cert.getSubjectX500Principal().getName())
                    .flatMap(this::getCertificateCNName)
                    .anyMatch(acceptedCNs::contains);
        }

        return false;
    }

    private Stream<String> getCertificateCNName(String x500PrincipalName) {
        try {
            return new LdapName(x500PrincipalName).getRdns().stream()
                    .filter(rdn -> rdn.getType().equalsIgnoreCase("cn"))
                    .map(rdn -> rdn.getValue().toString());
        } catch (Exception e) {
            logger.warn("Failed to get certificate CN.", e);
            return Stream.empty();
        }
    }

    private static Set<String> stringListToSet(String acceptedCNsString) {
        return Stream.of(acceptedCNsString.split(","))
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }

}