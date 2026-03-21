package com.lee.service.impl;

import com.lee.convert.IArticleConvert;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleDetailVo;
import com.lee.entity.Article.ArticleDto;
import com.lee.mapper.ArticleMapper;
import com.lee.security.JwtUtil;
import com.lee.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    private IArticleConvert articleConvert;

    public ArticleServiceImpl(IArticleConvert articleConvert) {
        this.articleConvert = articleConvert;
    }

    @Override
    public void addArticle(ArticleDto articleDto,String token){
        Article article =articleConvert.dto2Entity(articleDto,token);
        super.save(article);
    }

    @Override
    public ArticleDetailVo getOneDetailById(Integer id) {
        Article article = this.getById(id);
        ArticleDetailVo articleDetailVo = articleConvert.entity2DetailVo(article);
        articleDetailVo.setViewNumber(articleDetailVo.getViewNumber()+1);
        return articleDetailVo;
    }

    @Override
    public void deleteArticleById(Integer id,String token) {
        Long currentUserId = null;
        if (JwtUtil.validateToken(token))
        {
            currentUserId = JwtUtil.getUserIdInt(token);
        }else{
            System.out.println("解析失败");
        }
        Article article = this.getById(id);
        if (article == null){
            throw new RuntimeException("博客不存在");
        }
        if (!article.getUserId().equals(currentUserId)) {
            throw new RuntimeException("无权限删除");
        }
        this.removeById(article);
    }


}
