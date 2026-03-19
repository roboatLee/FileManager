package com.lee.controller;

import com.lee.dto.LoginRequest;
import com.lee.dto.LoginResponse;
import com.lee.dto.RegisterRequest;
import com.lee.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${test.flag}")
    private String ans;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return "注册成功";
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/ans")
    public  String getAns(){
        return ans;
    }
}