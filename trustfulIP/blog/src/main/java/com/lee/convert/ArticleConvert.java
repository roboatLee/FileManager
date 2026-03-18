package com.lee.convert;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleVo;
import org.mapstruct.Mapper;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */
@Mapper(componentModel = "spring")
public interface ArticleConvert {
    ArticleVo entity2vo(Article user);
}
