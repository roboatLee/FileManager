package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.dto.LoginRequest;
import com.lee.dto.LoginResponse;
import com.lee.dto.RegisterRequest;
import com.lee.entity.User;
import com.lee.mapper.UserMapper;
import com.lee.security.JwtUtil;
import com.lee.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegisterRequest request) {

        User exist = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", request.getUsername())
        );

        if (exist != null) {
            throw new RuntimeException("用户名已存在");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole("USER");
        user.setStatus((byte) 1);

        userMapper.insert(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {


        User user = userMapper.selectOne(
                new QueryWrapper<User>().eq("username", request.getUsername())
        );

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        String token = JwtUtil.generateToken(user);

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
