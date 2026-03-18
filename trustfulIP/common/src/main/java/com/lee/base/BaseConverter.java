package com.lee.base;

import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/3/17
 */

/**
 * 类型转换基类
 *
 * @param <T> 实体类
 * @param <B> Bo类
 * @param <V> VO类
 * @param <Dto> Dto类
 */

public interface BaseConverter<T, V, B, Dto> {

    /**
     * 实体类转VO：将一个标准的实体类对象转换为用于在前面页面中以列表项的形式展示的VO对象
     *
     * @param t 待转换的标准实体对象
     * @return
     */
    V entity2vo(T t);

    /**
     * 实体类集合转VO集合
     *
     * @param tList
     * @return
     */
    List<V> entityList2voList(List<T> tList);

}