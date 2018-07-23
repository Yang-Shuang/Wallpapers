package com.yang.wallpapers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by
 * yangshuang on 2018/7/23.
 */

public class JsonParser {
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    public static <T> T json2Bean(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    public static HashMap<String, String> json2Map(String json) throws IOException {
        if (json == null || json.equals("")) {
            return null;
        }
        return mapper.readValue(json, HashMap.class);
    }

    public static <T> List<T> json2List(String json, Class<T> clazz) throws IOException {
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        return mapper.readValue(json, listType);
    }

    public static String bean2Json(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }


    public static List<HashMap<String, Object>> jsonFile2List(File file) throws IOException {
        return mapper.readValue(file, new TypeReference<List<HashMap<String, Object>>>() {
        });
    }

    public static HashMap<String, Object> jsonFile2Map(File file) throws IOException {
        return mapper.readValue(file, new TypeReference<HashMap<String, Object>>() {
        });
    }

    public static HashMap<String, String> bean2Map(Object t) throws JsonProcessingException, IOException {
        String json = mapper.writeValueAsString(t);
        return json2Map(json);
    }
}
