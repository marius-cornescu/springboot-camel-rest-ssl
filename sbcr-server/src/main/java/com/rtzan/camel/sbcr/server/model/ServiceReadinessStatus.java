package com.rtzan.camel.sbcr.server.model;

public class ServiceReadinessStatus extends ServiceStatus {

    private final String version;

    public ServiceReadinessStatus(String serviceName, String apiVersion, String apiMode, String status) {
        super(serviceName, apiVersion, apiMode, status);
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
