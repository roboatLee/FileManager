package com.lee.dao;

import lombok.Data;

/**
 * @author KitenLee
 * * @date 2026/2/10
 */
@Data
public class ChatMessage {
    private Long sender;
    private String content;
    private long time;
    private String conversationId;

}
