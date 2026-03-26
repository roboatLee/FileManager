package com.lee.persistence.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author KitenLee
 * * @date 2026/3/26
 */
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();


    public  static String toJson(Object obj) {
        if (obj == null) return null;
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("JSON转换失败", e);
        }
    }
}
