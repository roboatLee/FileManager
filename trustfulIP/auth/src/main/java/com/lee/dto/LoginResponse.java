package com.lee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;

    private Long userId;

    private String username;

    private String role;
}