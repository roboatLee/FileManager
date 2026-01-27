package com.lee.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lee.service.FileService;
import com.lee.service.MyJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/5
 */
@Service
public class MyJsonImpl implements MyJsonService {
    @Autowired
    private FileService fileService;
    /**
     * String_Array Extract List_Json
     * */
    @Override
    public List<JsonNode> extractArrayJson(String s){
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrary = null;

        try{
            arrary = (ArrayNode) mapper.readTree(s);
        }catch (Exception e){
            System.out.println(e);
        }

        List<JsonNode> Jsons = new ArrayList<>();
        for (JsonNode sub : arrary){
            Jsons.add(sub);
        }
        return  Jsons;
    }

    /**
     * 获得Json里面的属性里面的内容
     * */
    @Override
    public  String getAttributeDataInJson(JsonNode jsonNode,String arrtibute){
        return jsonNode.get(arrtibute).asText();
    }

    /**
     *  将node里面的属性解析提取到一个json列表中
     *  将attribute属性放到一个json列表
     *  是一个 数组
     * */
    @Override
    public ArrayNode getAttributeInJson(JsonNode jsonNode){
        //创建mapper属性
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode attributeAnswer = mapper.createArrayNode();

        Iterator<String> fieldNames = jsonNode.fieldNames();

        /**
         * 将 node 添加到
         * */
        while (fieldNames.hasNext()) {
            ObjectNode newNode = mapper.createObjectNode();
            String fieldName = fieldNames.next();
            newNode.put("name",fieldName);
            attributeAnswer.add(newNode);
        }
        return attributeAnswer;
    }

    /**
     * 根据属性将youtube中断开的句子拼接到一起
     * 将分割开的句子连接到一起。
     *如果是.那么就是换行
     * */
    @Override
    public String getContentInAttribute2txt(List<JsonNode> Jsons,String attribute){
        String Final = new String();

        for (JsonNode json : Jsons){
            String line =  getAttributeDataInJson(json,attribute);
            line = line.replace("\n"," ");
            line = line.replace(".",".\n");
            Final += line;
        }
        return  Final;
    }

    @Override
    public String getJsonFile2txt(String path,String name){
        return getContentInAttribute2txt(extractArrayJson(fileService.readFileContent(path,name)),"content");
    }


}
