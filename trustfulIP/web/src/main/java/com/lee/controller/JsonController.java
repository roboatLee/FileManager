package com.lee.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.lee.dao.file.OnePathFilesVo;
import com.lee.service.FileService;
import com.lee.service.MyJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author KitenLee
 * * @date 2026/1/4
 */
@RestController
@RequestMapping("/api/file")
public class JsonController {

    @Autowired
    private FileService fileService;

    @Autowired
    private MyJsonService myJsonService;

    @GetMapping("/getdefaultfiles")
    public OnePathFilesVo getDefaultFiles(){
        return fileService.getDefaultFiles();
    }

    @GetMapping("/getparent")
    public  JsonNode getParetn(@RequestParam String path){
        return fileService.getParentFiles(path);
    }

    @GetMapping("/getchild")
    public JsonNode getChild(@RequestParam String path){
        return fileService.getDir(path);
    }

    @GetMapping("/getcontenttxt")
    public String getJsonFile2txt(@RequestParam String path,@RequestParam String name){return  myJsonService.getJsonFile2txt(path,name);}

}
