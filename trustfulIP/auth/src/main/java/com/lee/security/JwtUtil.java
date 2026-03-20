package com.lee.security;

import com.lee.entity.User;
import io.jsonwebtoken.Claims;
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

    public static String generateToken(User user) {
        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public static Claims parseToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static int getUserIdInt(String token) {
        Integer userId = parseToken(token).get("userId", Integer.class);
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
