package com.lee.service;

import com.lee.dto.LoginRequest;
import com.lee.dto.LoginResponse;
import com.lee.dto.RegisterRequest;
import com.lee.entity.User;
import com.lee.entity.user.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
public interface AuthService {
    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
    UserVo getUserById(HttpServletRequest request);
    Map<String, Object> refresh( Map<String, String> body);
}
