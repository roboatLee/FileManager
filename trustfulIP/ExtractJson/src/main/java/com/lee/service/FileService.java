package com.lee.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/1/4
 */
public interface FileService {

    /**
     * 打印当前文件
     * */
    void printNowFile(File file);

    /**
     * 获得文件内容
     * */
    String getFileContent(File f);

    /**
     * 根据路径和名字获得文件内容
     * */
    String getFileContent(String path,String name);

    /**
     * 获得当前文件的所有文件和目录
     * */
    List<File> getAllFileAndDir(File f);

    /**
     *
     * 将当前的文件的文件所在目录和所有文件以json格式展示
     *
     * @return
     * {
     *     path: path
     *     file:[
     *      filename1,
     *      filename2
     *     ]
     * }
     * */
    JsonNode getAllFileAndDirInJson(File f);

    JsonNode getFiles2Json(List<File> files);

    JsonNode getDir(String  path);

    /**
     * 打开默认文件夹
     * */
    JsonNode getDefaultFiles();

    /**
     * 打开上一级文件
     * */
    JsonNode getParentFiles(String path);

    /**
     * 打开path目录
     * */
    JsonNode getPathFiles(String path);


    /**
     * 判断是否为文件
     * */
    String isFile (File file);
}
