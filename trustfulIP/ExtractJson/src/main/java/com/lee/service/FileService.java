package com.lee.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.lee.dao.file.FileType;
import com.lee.dao.file.OnePathFilesVo;
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
    String readFileContent(File f);
    /**
     * 根据路径和名字获得文件内容
     * */
    String readFileContent(String path,String name);
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
    OnePathFilesVo getAllFileAndDirInDirAsDao(File f);
    OnePathFilesVo getAllFileAndDirAsDao(File f);
    JsonNode getAllFileAndDirInJsonAsJson(File f);
    OnePathFilesVo makeFiles2DDao(File f, List<File> files);
    JsonNode getFiles2Json(List<File> files);
    OnePathFilesVo getDir(String  path);
    JsonNode getParentFilesAsJson(String path);
    JsonNode getDirAsJson(String  path);
    /**
     * 打开默认文件夹
     * */
    JsonNode getDefaultFilesAsJson();
    OnePathFilesVo getDefaultFiles();
    /**
     * 打开上一级文件
     * */
    OnePathFilesVo getParentFiles(String path);
    /**
     * 打开path目录
     * */
    JsonNode getPathFiles(String path);
    /**
     * 判断是否为文件
     * */
    FileType isFile (File file);
    /**
     * 展示磁盘内容
     * */
    List<String> getDisks();

    /**
     * todo: 写文件
     * */
    void writeFileContent();



}
