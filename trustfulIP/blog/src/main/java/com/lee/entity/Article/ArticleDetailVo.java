package com.lee.entity.Article;

import com.lee.entity.user.UserVo;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author KitenLee
 * @date 2026/3/20
 * 这个其实是单独的Article的一个页面
 * 而ArticleVO其实更像一个列表里面的一个VO
 */
@Data
public class ArticleDetailVo {
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

    /**
     * 作者名字
     * */
    private UserVo userVo;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private Integer likeNumber;

    private Integer viewNumber;

}
