package com.lee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lee.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
@Mapper
public interface UserMapper  extends BaseMapper<User> {

}
