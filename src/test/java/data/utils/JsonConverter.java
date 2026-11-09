package data.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonConverter {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonConverter() {}
    
    public static String convertToJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not serialize request payload", e);
        }
    }
}
