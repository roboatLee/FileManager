package com.lee.controller;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleDto;
import com.lee.entity.Article.ArticleVo;
import com.lee.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lee
 * @since 2026-03-17
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private IArticleService articleService;

    @GetMapping("/getAllArticle")
    public List<ArticleVo> getAllArtile(){
        return null;
    }

    @PostMapping("/addArticle")
    public void addArticle(@RequestBody ArticleDto articleDto){
        articleService.addArticle(articleDto);
    }

}
