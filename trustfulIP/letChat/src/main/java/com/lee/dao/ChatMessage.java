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
    private String sender;
    private String content;
    private long time;
    private String conversationId;

}
