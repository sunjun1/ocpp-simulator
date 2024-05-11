package com.incarcloud.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author jack
 */
@Slf4j
public class JsonSchemaUtil {

    private static Map<String, JsonNode> cacheMap = new HashMap<>();

    public static boolean check(JsonNode jsonNode, JsonNode schemaNode) {
        ProcessingReport report = JsonSchemaFactory.byDefault().getValidator().validateUnchecked(schemaNode, jsonNode);
        if (report.isSuccess()) {
            return true;
        } else {
            Iterator<ProcessingMessage> it = report.iterator();
            while (it.hasNext()) {
                ProcessingMessage pm = it.next();
                log.warn(pm.toString());
            }
            return false;
        }
    }

    public static JsonNode getJsonNodeFromString(String jsonStr) {
        try {
            return JsonLoader.fromString(jsonStr);
        } catch (IOException e) {
            log.error("Json格式错误，json:{}", jsonStr);
            return null;
        }
    }

    public static JsonNode getJsonNodeFromFile(String filePath) {
        try {
            if (cacheMap.containsKey(filePath)) {
                return cacheMap.get(filePath);
            } else {
                InputStreamReader inputStreamReader = new InputStreamReader(JsonSchemaUtil.class.getClassLoader().getResourceAsStream(filePath));
                JsonNode jsonNode = new JsonNodeReader().fromReader(inputStreamReader);
                cacheMap.put(filePath, jsonNode);
                return jsonNode;
            }
        } catch (FileNotFoundException e) {
            log.error("文件地址错误，filePath:{}", filePath);
            return null;
        } catch (IOException e) {
            log.error("Json格式错误，filePath:{}", filePath);
            return null;
        }
    }

}
