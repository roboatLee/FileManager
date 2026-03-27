package com.lee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lee.convert.UserConvert;
import com.lee.dto.LoginRequest;
import com.lee.dto.LoginResponse;
import com.lee.dto.RegisterRequest;
import com.lee.entity.User;
import com.lee.entity.user.UserVo;
import com.lee.mapper.UserMapper;
import com.lee.security.JwtUtil;
import com.lee.service.AuthService;
import com.lee.service.IUserService;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
@Service
public class AuthServiceImpl implements AuthService {

    private  UserMapper userMapper;
    private  PasswordEncoder passwordEncoder;
    private UserConvert userConvert;
    private IUserService userService;

    public AuthServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, UserConvert userConvert, IUserService userService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userConvert = userConvert;
        this.userService = userService;
    }


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
        String refreshToken = JwtUtil.generateRefreshToken(user);

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getRole(),
                refreshToken
        );
    }

    @Override
    public UserVo getUserById(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println(token);
        System.out.println(JwtUtil.getUserIdInt(token));
        User user = userMapper.selectById(JwtUtil.getUserIdInt(token));
        System.out.println(user);
        return userConvert.Entity2Vo(user);
    }

    @Override
    public Map<String, Object> refresh(Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        try {
            Claims claims = JwtUtil.parseToken(refreshToken);

            if (!"refresh".equals(claims.getSubject())) {
                throw new RuntimeException("非法 token");
            }

            Long userId = claims.get("userId", Long.class);

            // 可以查数据库确认用户存在
            User user = userService.getById(userId);

            String newAccessToken = JwtUtil.generateToken(user);
            Map<String, Object> result = new HashMap<>();
            result.put("accessToken", newAccessToken);
            result.put("refreshToken", refreshToken);

            return result;

        } catch (Exception e) {
            throw new RuntimeException("refresh_token 失效，请重新登录");
        }
    }
}
