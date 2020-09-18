package com.rtzan.camel.sbcr.server.model;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public class ServiceStatus {

    private String serviceName;
    private String version;
    private String status;

    private Map<String, String> attributes;

    public ServiceStatus() {
    }

    public ServiceStatus(String serviceName, String version, String status) {
        this.serviceName = serviceName;
        this.version = version;
        this.status = status;
        this.attributes = extractAttributes();
    }

    private static Map<String, String> extractAttributes() {
        try {
            Attributes attribs = new Manifest(ServiceStatus.class.getResourceAsStream("/META-INF/MANIFEST.MF")).getMainAttributes();

            return attribs.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue().toString())
                    );
        } catch (Exception e) {
            return new HashMap<>();
        }
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

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "Status{" +
                "version='" + version + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
