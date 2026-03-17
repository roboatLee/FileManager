package com.lee.blog.controller;

import com.lee.blog.entity.Article;
import com.lee.blog.service.IArticleService;
import com.lee.blog.service.impl.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2026-03-17
 */
@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    public IArticleService articleService = new ArticleServiceImpl();

    @GetMapping("/getAllArticle")
    public List<Article> getAllArtile(){
        return articleService.list();
    }
    

}
