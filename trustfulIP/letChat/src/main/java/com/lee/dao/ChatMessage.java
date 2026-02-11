package com.lee.dao;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import java.time.LocalDateTime;

/**
 * @author KitenLee
 * * @date 2026/2/10
 */
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    private LocalDateTime createdAt = LocalDateTime.now();
}
