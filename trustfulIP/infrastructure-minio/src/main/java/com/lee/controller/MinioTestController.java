package com.lee.controller;

import com.lee.properties.MinioProperties;
import com.lee.service.MinioService;
import io.minio.MinioClient;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.InputStream;

/**
 * @author KitenLee
 * * @date 2026/3/19
 */
@RestController
@RequestMapping("/minio")
public class MinioTestController {

    private final MinioService minioService;

    public MinioTestController(MinioService minioService) {
        this.minioService = minioService;
    }


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return minioService.upload(file);
    }


}
