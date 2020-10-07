package com.rtzan.camel.sbcr.server.model;

import java.util.Map;

public class ServiceFullStatus extends ServiceStatus {

    private final Map<String, String> attributes;

    public ServiceFullStatus(String serviceName, String apiVersion, String apiMode, String status) {
        super(serviceName, apiVersion, apiMode, status);
        this.attributes = ApplicationAttributes.getAttributes();
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return super.toString() +
                "ServiceFullStatus{" +
                '}';
    }
}
