package com.lee.entity.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author KitenLee
 * * @date 2026/3/20
 */
@Data
public class UserVo {

    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private String role;

    /**
     * 角色头像
     * */
    private String avatarUrl;
}
