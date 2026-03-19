package com.lee.convert;

import com.lee.base.BaseConverter;
import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleBo;
import com.lee.entity.Article.ArticleDto;
import com.lee.entity.Article.ArticleVo;
import com.lee.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */
@Mapper(componentModel = "spring")
public interface ArticleConvert extends BaseConverter<Article,ArticleVo, ArticleBo, ArticleDto> {

//    @Mapping(target = "userName", source = "userId", qualifiedByName = "mapUserIdToUserName")
//    ArticleVo toVo(Article article);
//
//    @Named("mapUserIdToUserName")
//    default String mapUserIdToUserName(Integer userId) {
//        // 调用 UserRepository 查询 username
//        return userRepository.findById(userId)
//                .map(User::getUsername)
//                .orElse("未知用户");
//    }
}
