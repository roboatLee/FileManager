package com.lee.service.impl;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleDto;
import com.lee.mapper.ArticleMapper;
import com.lee.security.JwtUtil;
import com.lee.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public void addArticle(ArticleDto articleDto,String token){
        Article article =dto2Entity(articleDto,token);
        super.save(article);
    }


    public Article dto2Entity(ArticleDto articleDto,String token){
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setHtmlContent(articleDto.getHtmlContent());
        article.setMarkdownContent(articleDto.getMarkdownContent());
        article.setCreateTime(LocalDateTime.now());
        article.setCreateTime(LocalDateTime.now());
        article.setLikeNumber(0);
        article.setViewNumber(0);

        if (JwtUtil.validateToken(token))
        {
            article.setUserId(JwtUtil.getUserIdInt(token));
        }else{
            System.out.println("解析失败");
        }


        return article;
    }




}
