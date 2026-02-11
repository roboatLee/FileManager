package com.lee.dao;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author KitenLee
 * * @date 2026/2/10
 */
@Data
public class ChatMessageVo {
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
}
