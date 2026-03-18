package com.lee.convert;

import com.lee.entity.Article.Article;
import com.lee.entity.Article.ArticleVo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-18T19:47:10+0800",
    comments = "version: 1.6.3, compiler: javac, environment: Java 1.8.0_361 (Oracle Corporation)"
)
@Component
public class ArticleConvertImpl implements ArticleConvert {

    @Override
    public ArticleVo entity2vo(Article t) {
        if ( t == null ) {
            return null;
        }

        ArticleVo articleVo = new ArticleVo();

        articleVo.setTitle( t.getTitle() );
        articleVo.setMarkdownContent( t.getMarkdownContent() );
        articleVo.setHtmlContent( t.getHtmlContent() );

        return articleVo;
    }

    @Override
    public List<ArticleVo> entityList2voList(List<Article> tList) {
        if ( tList == null ) {
            return null;
        }

        List<ArticleVo> list = new ArrayList<ArticleVo>( tList.size() );
        for ( Article article : tList ) {
            list.add( entity2vo( article ) );
        }

        return list;
    }
}
