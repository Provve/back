package tech.provve.util;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.ext.javatime.JavaTimeInitializer;
import tools.jackson.databind.json.JsonMapper;

import java.util.Map;

public class Jackson {

    private Jackson() {
    }

    public static final ObjectMapper json = JsonMapper.builder()
                                                      .registerSubtypes(JavaTimeInitializer.class)
                                                      .enable(SerializationFeature.INDENT_OUTPUT)
                                                      .build();

    public static Map<String, Object> convertToMap(Object object) {
        return Jackson.json.convertValue(object, new TypeReference<>() {
        });
    }

    public static <T> T convertToClass(Map<String, Object> map, Class<T> clazz) {
        return Jackson.json.convertValue(map, clazz);
    }
}
