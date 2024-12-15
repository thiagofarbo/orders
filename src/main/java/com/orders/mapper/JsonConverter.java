package com.orders.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonConverter {

    private final ObjectMapper objectMapper;

    /**
     * Convert any object to a String JSON.
     *
     * @param object Object to be converted
     * @param <T>    Generic type object
     * @return String JSON
     * @throws RuntimeException If there is an error in the conversion
     */
    public <T> String toJsonString(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (RuntimeException | JsonProcessingException e) {
            throw new RuntimeException("Error to convert object to JSON: " + object.getClass().getName(), e);
        }
    }

}
