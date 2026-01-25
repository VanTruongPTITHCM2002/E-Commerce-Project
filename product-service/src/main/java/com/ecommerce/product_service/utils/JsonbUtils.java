package com.ecommerce.product_service.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class JsonbUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonbUtils() {
        // utility class
    }

    /**
     * Convert JSON string (jsonb) to JsonNode
     */
    public static JsonNode toJsonNode(String json) {
        if (json == null || json.isBlank()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Invalid jsonb value", e);
        }
    }

    /**
     * Convert JsonNode to JSON string (for saving to jsonb)
     */
    public static String toJsonString(JsonNode node) {
        if (node == null || node.isNull()) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(node);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot convert JsonNode to String", e);
        }
    }

    /**
     * Create empty json object {}
     */
    public static ObjectNode emptyObject() {
        return OBJECT_MAPPER.createObjectNode();
    }

    /**
     * Safe get field from JsonNode
     */
    public static JsonNode get(JsonNode node, String field) {
        if (node == null || node.isNull()) {
            return null;
        }
        return node.get(field);
    }
}

