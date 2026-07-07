package data.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonConverter {
    private JsonConverter() {}
    
    public static String convertToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = null;
        try {
            jsonPayload = objectMapper.writeValueAsString(object);           
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return jsonPayload;
    }
}
