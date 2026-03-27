package com.lee.security;

import com.lee.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
public class JwtUtil {

    private static final String SECRET = "LiChengXinAiZhaoXinLing5201314LiChengXinAiZhaoXinLing";
    private static final long EXPIRE = 86400000;
    private static final long REFRESH_EXPIRE = 7 * 24 * 60 * 60 * 1000L;


    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static String generateRefreshToken(User user) {
        return Jwts.builder()
                .setSubject("refresh")
                .claim("userId", user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static Claims parseToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        System.out.println(token);
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Claims parseExpiredToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // 即使过期，也能拿到 claims
            return e.getClaims();
        }
    }


    public static Long getUserIdInt(String token) {
        Long userId = parseToken(token).get("userId", Long.class);
        return userId;
    }


    public static boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
