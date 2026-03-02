package com.lee.dto;

import lombok.Data;

/**
 * @author KitenLee
 * * @date 2026/3/2
 */
@Data
public class RegisterRequest {

    private String username;

    private String password;

    private String email;
}
