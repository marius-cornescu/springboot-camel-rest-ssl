package com.rtzan.camel.sbcr.server.model;

import java.util.Map;

public class ServiceStatus {

    private final String serviceName;
    private final String apiVersion;
    private final String status;

    private final Map<String, String> attributes;

    public ServiceStatus(String serviceName, String apiVersion, String status) {
        this(serviceName, apiVersion, status, ApplicationAttributes.getAttributes());
    }

    ServiceStatus(String serviceName, String apiVersion, String status, Map<String, String> attributes) {
        this.serviceName = serviceName;
        this.apiVersion = apiVersion;
        this.status = status;
        this.attributes = attributes;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public String getStatus() {
        return status;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Status{" +
                "apiVersion='" + apiVersion + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
