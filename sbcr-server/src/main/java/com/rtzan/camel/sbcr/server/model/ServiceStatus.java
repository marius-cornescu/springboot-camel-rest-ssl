package com.rtzan.camel.sbcr.server.model;

public class ServiceStatus {

    private String serviceName;
    private String version;
    private String status;

    public ServiceStatus() {
    }

    public ServiceStatus(String serviceName, String version, String status) {
        this.serviceName = serviceName;
        this.version = version;
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "version='" + version + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
