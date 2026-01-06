package com.lee.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.lee.service.FileService;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/4
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public void printNowFile(File file){
        System.out.println("目前操作的文件为"+file);
    }

    /**
     * 获取FileContent
     * */
    @Override
    public  String getFileContent(File f){
        /**
         * 初始化FileReader对象
         * */
        Reader reader = null;
        try{
            reader = new FileReader(f);
        }catch (Exception e){
            System.out.println(e);
        }
        int n = 0;
        StringBuilder FileContent = new StringBuilder();
        for (;;){

            try {
                n = reader.read();
            }catch (Exception e){
                System.out.println(e);
            }
            if(n == -1){
                break;
            }
            FileContent.append((char) n);
        }
        /**
         * 关闭reader
         * */
        try{
            reader.close();
        }catch (Exception e){
            System.out.println(e);
        }
        return FileContent.toString();
    }

    /**
     * 根据路径和名字，获取FileContent
     * */
    @Override
    public String getFileContent(String path,String name){
        File f = new File(path+"\\"+name);
        return getFileContent(f);
    }

    @Override
    public List<File> getAllFileAndDir(File f){
        File parent =  f.getParentFile();
        return Arrays.asList(parent.listFiles());
    }
    /**
     *
     *
     * 将当前的文件的文件所在目录和所有文件以json格式展示
     *
     * @return
     * {
     *     path: path
     *     file:[
     *     {
     *         name: filename1,
     *         type: file
     *     }
     *      ,
     *      {
     *          name: filename1,
     *          type: dir
     *      }
     *     ]
     * }
     * */
    @Override
    public JsonNode getAllFileAndDirInJson(File f){
        List<File> files =  getAllFileAndDir(f);
        return getFiles2Json(files);
    }

    @Override
    public JsonNode getDir(String  path){
        File file = new File(path);
        List<File> files = Arrays.asList(file.listFiles());
        return getFiles2Json(files);
    }

    @Override
    public JsonNode getFiles2Json(List<File> files){
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        String path = files.get(0).getParentFile().getPath();

        root.put("path",path);
        ArrayNode array = mapper.createArrayNode();

        for (File oneFile : files){
            ObjectNode file = mapper.createObjectNode();
            file.put("name",oneFile.getName());
            file.put("type",isFile(oneFile));
            array.add(file);
        }
        root.set("files",array);
        return  root;
    }

    @Override
    public JsonNode getDefaultFiles(){
        File file = new File("D:\\User\\document\\Json\\Probaility\\L01.1.json");
        return getAllFileAndDirInJson(file);
    }

    @Override
    public JsonNode getParentFiles(String path){
        File file = new File(path);
        return getAllFileAndDirInJson(file);
    }

    @Override
    public JsonNode getPathFiles(String path){
        File file = new File(path);

        return getAllFileAndDirInJson(file);
    }

    @Override
    public String isFile (File file){
            if (file.isDirectory()) {
                return "dir";
            } else if (file.isFile()) {
                return "file";
            }
            else return "err";
    }



}
