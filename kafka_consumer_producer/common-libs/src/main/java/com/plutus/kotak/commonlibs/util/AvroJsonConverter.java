package com.plutus.kotak.commonlibs.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class AvroJsonConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Map<String, Object> convertAvroToJson(SpecificRecord avroRecord) {
        Map<String, Object> jsonMap = new HashMap<>();
        
        for (org.apache.avro.Schema.Field field : avroRecord.getSchema().getFields()) {
            try {
                Object value = avroRecord.get(field.pos());
                jsonMap.put(field.name(), convertValue(value));
            } catch (Exception e) {
                // Log the error but continue processing other fields
                System.err.println("Error processing field " + field.name() + ": " + e.getMessage());
            }
        }
        
        return jsonMap;
    }

    private Object convertValue(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof ByteBuffer) {
            return new String(((ByteBuffer) value).array());
        }

        if (value instanceof LocalDateTime) {
            return ((LocalDateTime) value).format(DATE_FORMATTER);
        }

        return value;
    }

    public String convertToJsonString(Map<String, Object> data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException("Error converting to JSON string", e);
        }
    }

    public <T> T convertJsonToObject(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to object", e);
        }
    }
} 