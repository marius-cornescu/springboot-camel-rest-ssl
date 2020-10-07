package com.rtzan.camel.sbcr.server.model;

public abstract class ServiceStatus {

    private final String serviceName;
    private final String apiVersion;
    private final String status;
    private final String apiMode;

    ServiceStatus(String serviceName, String apiVersion, String apiMode, String status) {
        this.serviceName = serviceName;
        this.apiVersion = apiVersion;
        this.apiMode = apiMode;
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getApiMode() {
        return apiMode;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "apiVersion='" + apiVersion + '\'' +
                ", apiMode='" + apiMode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
