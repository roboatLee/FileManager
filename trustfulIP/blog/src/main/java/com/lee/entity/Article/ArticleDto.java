package com.lee.entity.Article;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */
public class ArticleDto {

    /**
     * 标题
     */
    private String title;

    /**
     * html内容
     */
    private String htmlContent;


    /**
     * markdown内容
     */
    private String markdownContent;




}
