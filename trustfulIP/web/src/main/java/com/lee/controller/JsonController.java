package com.lee.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.lee.dao.file.MarkDownFile.MarkDownFileDto;
import com.lee.dao.file.OnePathFilesVo;
import com.lee.service.FileService;
import com.lee.service.MyJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public  OnePathFilesVo getParetn(@RequestParam String path){
        return fileService.getParentFiles(path);
    }

    @GetMapping("/getchild")
    public OnePathFilesVo getChild(@RequestParam String path){
        return fileService.getDir(path);
    }

    @GetMapping("/getcontenttxt")
    public String getJsonFile2txt(@RequestParam String path,@RequestParam String name){return  myJsonService.getJsonFile2txt(path,name);}

    @GetMapping("/ChangeVolume")
    public  OnePathFilesVo ChangeVolume(@RequestParam String path){return fileService.getDir(path);}

    @GetMapping("/getDisk")
    public List<String> getDisks(){return  fileService.getDisks();}

    @GetMapping("/readfile")
    public String readFileContent(@RequestParam String path,@RequestParam String name){return fileService.readFileContent(path,name);}

    @PostMapping("/saveMarkdown")
    public void saveMarkdown(@RequestBody MarkDownFileDto dto) {
        fileService.writeFileContent(dto);
    }


}
