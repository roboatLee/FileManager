package com.lee.entity.Article;


import com.lee.entity.user.UserVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */
@Getter
@Setter
@ToString
public class ArticleVo {
    /**
     * 主键
     */
    private Long id;
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
    private UserVo user;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
