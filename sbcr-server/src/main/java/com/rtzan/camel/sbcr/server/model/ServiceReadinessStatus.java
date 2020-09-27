package com.rtzan.camel.sbcr.server.model;

import java.util.Collections;

public class ServiceReadinessStatus extends ServiceStatus {

    private final String version;

    public ServiceReadinessStatus(String serviceName, String apiVersion, String status) {
        super(serviceName, apiVersion, status, Collections.emptyMap());
        this.version = ApplicationAttributes.getAttribute("Implementation-Version", "N/A");
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return super.toString() +
                "ServiceReadinessStatus{" +
                "version='" + version + '\'' +
                '}';
    }
}
