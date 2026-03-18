package com.lee.service;

import com.lee.dao.ChatMessage;

import java.io.IOException;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/2/10
 */

public interface ChatMessageRepository {
    void addMessage(ChatMessage msg) throws IOException;
    List<ChatMessage> getMessages() throws IOException;
    Long getId(ChatMessage entity);
}
