package com.lee.service.impl;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleDto;
import com.lee.mapper.ArticleMapper;
import com.lee.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2026-03-17
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    public void addArticle(ArticleDto articleDto){
        Article article = new Article();

    }





}
