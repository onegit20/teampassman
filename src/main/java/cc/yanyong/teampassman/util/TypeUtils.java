package cc.yanyong.teampassman.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TypeUtils {
    public static <T> T convertObjectToType(Object object, Class<T> tClass) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String json = objectMapper.writeValueAsString(object);
            return objectMapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to convert object to type " + tClass.getName(), e);
        }
    }
}
