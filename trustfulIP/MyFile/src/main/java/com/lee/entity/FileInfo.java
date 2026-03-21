package com.lee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author lee
 * @since 2026-03-21
 */
@Getter
@Setter
@ToString
@TableName("file_info")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * minio路径
     */
    private String objectName;

    /**
     * 访问网址
     */
    private String url;

    /**
     * 大小
     */
    private Long size;

    /**
     * 类型
     */
    private String contentType;

    /**
     * 上传用户
     */
    private Long uploaderId;

    /**
     * 时间
     */
    private LocalDateTime createTime;
}
