package com.lee.service;

import com.lee.entity.Article.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lee.entity.Article.ArticleDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2026-03-17
 */
public interface IArticleService extends IService<Article> {
    void addArticle(ArticleDto articleDto);
}
