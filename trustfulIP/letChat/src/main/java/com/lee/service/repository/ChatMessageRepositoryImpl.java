package com.lee.service.repository;

import com.lee.dao.ChatMessage;
import com.lee.service.ChatMessageRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/2/10
 */
public class ChatMessageRepositoryImpl implements ChatMessageRepository {
    List<ChatMessage> messages = new ArrayList<>();

    @Override
    public void addMessage(ChatMessage msg) throws IOException {

    }

    @Override
    public List<ChatMessage> getMessages() throws IOException {

    }
}
