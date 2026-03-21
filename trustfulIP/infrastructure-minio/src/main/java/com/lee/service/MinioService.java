package com.lee.service;

import com.lee.properties.MinioProperties;
import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author KitenLee
 * * @date 2026/3/19
 */
@Service
public class MinioService {
    @Resource
    private  MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;

    @Getter
    @Value("${minio.bucket-name}")
    private String bucketName;

    /**
     * 查看bucket是否存在
     *
     * @return boolean
     */
    public boolean bucketExists() {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder()
                    .bucket(bucketName)
                    .build());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 创建存储bucket
     *
     * @return Boolean
     */
    public boolean createBucket() {
        if (bucketExists()) {
            return true;
        }
        try {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 上传文件
     *
     * @return
     */
    public String upload(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        return getString(file, fileName);
    }

    public String upload(MultipartFile file, String objectName){
        return getString(file, objectName);
    }

    @NotNull
    private String getString(MultipartFile file, String objectName) {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("上传失败", e);
        }

        return minioProperties.getEndpoint()
                + "/" + bucketName
                + "/" + objectName;
    }

    /**
     * 获取全部bucket
     */
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 创建文件夹或目录
     *
     * @param directoryName 目录路径
     */
    public boolean createDirectory(String directoryName) {
        if (!bucketExists()) {
            createBucket();
        }

        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(directoryName)
                    .stream(new ByteArrayInputStream(new byte[]{}), 0, -1)
                    .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }





}
