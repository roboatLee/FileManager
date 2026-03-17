package com.lee.blog.service.impl;

import com.lee.blog.entity.Article;
import com.lee.blog.mapper.ArticleMapper;
import com.lee.blog.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void addArticle(Article article){

    }

}
