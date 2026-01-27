package com.lee.test;

import com.lee.service.FileService;
import com.lee.service.impl.FileServiceImpl;

import java.io.File;

/**
 * @author KitenLee
 * * @date 2026/1/24
 */
public class MyTest {
    public static void main(String[] args) {
        FileService fileService = new FileServiceImpl();
        File f = new File("D:\\User\\project\\Knowledge\\00 计算机技术\\03自学项目\\FileManager\\日记开发\\1.23.md");
        System.out.println(fileService.readFileContent(f));
    }
}
