package com.lee.entity.Article;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */
@Getter
@Setter
@ToString
public class ArticleVo {
    /**
     * 标题
     */
    private String title;

    /**
     * markdown内容
     */
    private String markdownContent;

    /**
     * html内容
     */
    private String htmlContent;

}
