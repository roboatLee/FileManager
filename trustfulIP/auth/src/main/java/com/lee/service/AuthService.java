package com.lee.service;

import com.lee.dto.LoginRequest;
import com.lee.dto.LoginResponse;
import com.lee.dto.RegisterRequest;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
public interface AuthService {
    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
