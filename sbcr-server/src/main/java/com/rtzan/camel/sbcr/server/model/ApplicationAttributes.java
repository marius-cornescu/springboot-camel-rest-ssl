package com.rtzan.camel.sbcr.server.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

final class ApplicationAttributes {

    private static final Map<String, String> attributes = extractAttributes();

    static Map<String, String> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    static String getAttribute(String key, String defaultValue) {
        return attributes.getOrDefault(key, defaultValue);
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
}
