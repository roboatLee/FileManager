package com.lee.convert;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleDetailVo;
import com.lee.entity.Article.ArticleDto;
import com.lee.entity.Article.ArticleVo;
import com.lee.entity.User;
import com.lee.entity.user.UserVo;
import com.lee.security.JwtUtil;
import com.lee.service.IUserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/3/19
 */
@Service
public class IArticleConvert {
   private UserConvert userConvert;
    private IUserService userService;

    public IArticleConvert(UserConvert userConvert, IUserService userService) {
        this.userConvert = userConvert;
        this.userService = userService;
    }

    public ArticleVo entity2vo(Article article){

       ArticleVo articleVo = new ArticleVo();

       User user = userService.getById(article.getUserId());
        UserVo userVo = userConvert.Entity2Vo(user);

       articleVo.setHtmlContent(article.getHtmlContent());
       articleVo.setTitle(article.getTitle());
       articleVo.setMarkdownContent(article.getMarkdownContent());
        articleVo.setCreateTime(article.getCreateTime());
       articleVo.setUser(userVo);
        articleVo.setId(article.getId());

       return articleVo;
   }

   public List<ArticleVo> entityList2voList(List<Article> articles){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles){
            ArticleVo articleVo= entity2vo(article);
            articleVoList.add(articleVo);
        }
       System.out.println("entityList2voList执行成功");
        return  articleVoList;
   }

   public ArticleDetailVo entity2DetailVo(Article article){

        User user = userService.getById(article.getUserId());
       UserVo userVo = userConvert.Entity2Vo(user);

       ArticleDetailVo articleDetailVo = new ArticleDetailVo();

       articleDetailVo.setHtmlContent(article.getHtmlContent());
       articleDetailVo.setTitle(article.getTitle());
       articleDetailVo.setMarkdownContent(article.getMarkdownContent());
       articleDetailVo.setCreateTime(article.getCreateTime());
       articleDetailVo.setLikeNumber(article.getLikeNumber());
       articleDetailVo.setViewNumber(article.getViewNumber());
       articleDetailVo.setUserVo(userVo);

       return articleDetailVo;
   }

    public Article dto2Entity(ArticleDto articleDto, String token){
        Article article = new Article();
        article.setTitle(articleDto.getTitle());
        article.setHtmlContent(articleDto.getHtmlContent());
        article.setMarkdownContent(articleDto.getMarkdownContent());
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
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
