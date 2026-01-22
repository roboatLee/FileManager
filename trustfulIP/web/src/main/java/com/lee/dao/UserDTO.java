package com.lee.dao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * @author KitenLee
 * * @date 2026/1/22
 */
@Data
@Schema(description = "用户信息")
public class UserDTO {

    @Schema(description = "用户ID", example = "1")
    private Long id;

    @Schema(description = "用户名")
    @NotNull
    private String username;

    @Schema(description = "邮箱")
    @Email
    private String email;
}


