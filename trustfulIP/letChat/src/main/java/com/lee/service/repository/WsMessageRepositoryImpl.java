package com.lee.service.repository;

import com.lee.dao.ChatMessage;
import com.lee.persistence.json.JsonFileStore;
import com.lee.persistence.json.JsonRepository;
import com.lee.service.WsMessageRepository;

/**
 * @author KitenLee
 * * @date 2026/3/1
 */
public class WsMessageRepositoryImpl  extends JsonRepository<ChatMessage,Long> implements WsMessageRepository {

    protected WsMessageRepositoryImpl(JsonFileStore store, Class<ChatMessage> entityType) {
        super(store, entityType);
    }

    @Override
    protected Long getId(ChatMessage entity) {
        return null;
    }
}
