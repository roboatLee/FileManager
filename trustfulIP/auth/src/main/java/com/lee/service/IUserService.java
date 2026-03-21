package com.lee.service;

import com.lee.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lee
 * @since 2026-03-19
 */
public interface IUserService extends IService<User> {
    String uploadAvatar(Long userId, MultipartFile file);
    String uploadAvatar(MultipartFile file, HttpServletRequest request);

}
