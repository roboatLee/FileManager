package com.lee.convert;

import com.sun.xml.internal.bind.api.impl.NameConverter;
import org.mapstruct.Mapper;



/**
 * @author KitenLee
 * * @date 2026/3/17
 */
@Mapper(componentModel = "spring",uses = NameConverter.class)
public interface ArticleConvert {


}
