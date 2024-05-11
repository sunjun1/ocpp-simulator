package com.incarcloud.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * @author jack
 */
@Slf4j
public class OcppJsonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static ObjectMapper objectMapper2 = new ObjectMapper();
    static {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC+8"));
        objectMapper.setDateFormat(sdf);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf2.setTimeZone(TimeZone.getTimeZone("UTC+8"));
        objectMapper2.setDateFormat(sdf2);
        objectMapper2.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static <T> T decode(String content, Class<T> clazz) {
        try {
            return objectMapper.readValue(content, clazz);
        } catch (IOException e) {
            try {
                return objectMapper2.readValue(content, clazz);
            } catch (IOException e1) {
                log.error("Json转换异常(decode)", e1);
                throw new RuntimeException("Json转换异常");
            }
        }
    }

    public static <T> String encode(T content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (IOException e) {
            try {
                return objectMapper2.writeValueAsString(content);
            } catch (JsonProcessingException e1) {
                log.error("Json转换异常(encode)", e1);
                throw new RuntimeException("Json转换异常");
            }
        }
    }

    public static <T> String encode(List<T> content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (IOException e) {
            try {
                return objectMapper2.writeValueAsString(content);
            } catch (JsonProcessingException e1) {
                log.error("Json转换异常(encode)", e1);
                throw new RuntimeException("Json转换异常");
            }
        }
    }

    public static JsonNode readTree(String content) {
        try {
            return objectMapper.readTree(content);
        } catch (IOException e) {
            try {
                return objectMapper2.readTree(content);
            } catch (IOException e1) {
                log.error("Json转换异常(encode)", e1);
                throw new RuntimeException("Json转换异常");
            }
        }
    }

    public static boolean check(String content, String schema) {
        JsonNode jsonNode = JsonSchemaUtil.getJsonNodeFromString(content);
        if (jsonNode == null) {
            return false;
        }
        String filePath = "schema/" + schema + ".json";
        JsonNode schemaNode = JsonSchemaUtil.getJsonNodeFromFile(filePath);
        if (schemaNode == null) {
            return false;
        }
        return JsonSchemaUtil.check(jsonNode, schemaNode);
    }

}
