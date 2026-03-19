package com.lee.convert;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleVo;
import jdk.internal.dynalink.linker.LinkerServices;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/3/19
 */
@Service
public class IArticleConvert {
   private UserConvert userConvert;

    public IArticleConvert(UserConvert userConvert) {
        this.userConvert = userConvert;
    }

    public ArticleVo entity2vo(Article article){
       ArticleVo articleVo = new ArticleVo();
       articleVo.setHtmlContent(article.getHtmlContent());
       articleVo.setTitle(article.getTitle());
       articleVo.setMarkdownContent(article.getMarkdownContent());
        articleVo.setCreateTime(article.getCreateTime());
       articleVo.setUserName(userConvert.convertUserId2UserName(article.getUserId()));
       return articleVo;
   }

   public List<ArticleVo> entityList2voList(List<Article> articles){
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articles){
            ArticleVo articleVo= entity2vo(article);
            articleVoList.add(articleVo);
        }
        return  articleVoList;
   }
}
