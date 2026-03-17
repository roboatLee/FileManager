package com.lee.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2026-03-17
 */
@Getter
@Setter
@ToString
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * html内容
     */
    private String htmlContent;

    /**
     * UserId外键
     */
    private Integer userId;

    /**
     * markdown内容
     */
    private String markdownContent;

    /**
     * 1:草稿 2：已发布
     */
    private Byte status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更改时间
     */
    private LocalDateTime updateTime;

    private Integer like;

    private Integer view;
}
