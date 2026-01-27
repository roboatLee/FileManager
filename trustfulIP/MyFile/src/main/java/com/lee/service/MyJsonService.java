package com.lee.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;


import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/5
 */
public interface MyJsonService {
    /**
     * String_Array Extract List_Json
     * */
    List<JsonNode> extractArrayJson(String s);
    String getAttributeDataInJson(JsonNode jsonNode,String arrtibute);
    ArrayNode getAttributeInJson(JsonNode jsonNode);
    String getContentInAttribute2txt(List<JsonNode> Jsons,String attribute);
    String getJsonFile2txt(String path,String name);
}
