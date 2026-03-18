package com.lee.convert;

import com.lee.base.BaseConverter;
import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleBo;
import com.lee.entity.Article.ArticleDto;
import com.lee.entity.Article.ArticleVo;
import org.mapstruct.Mapper;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */
@Mapper(componentModel = "spring")
public interface ArticleConvert extends BaseConverter<Article,ArticleVo, ArticleBo, ArticleDto> {


}
