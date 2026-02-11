package com.lee.service.repository;

import com.lee.dao.ChatMessage;
import com.lee.persistence.json.JsonFileStore;
import com.lee.persistence.json.JsonRepository;
import com.lee.service.ChatMessageRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author KitenLee
 * * @date 2026/2/10
 */
@Repository
public class ChatMessageRepositoryImpl
        extends JsonRepository<ChatMessage,Long>
        implements ChatMessageRepository
{

    private final AtomicLong idGenerator = new AtomicLong(1);

    public  ChatMessageRepositoryImpl() {
        super(
                new JsonFileStore(Paths.get("data", "chat-messages.json")),
                ChatMessage.class
        );
    }

    @Override
    public void addMessage(ChatMessage entity) throws IOException {
        if (entity.getId() == null) {
            entity.setId(idGenerator.getAndIncrement());
        }
        super.save(entity);
    }

    @Override
    public List<ChatMessage> getMessages() throws IOException {
        return null;
    }

    @Override
    public Long getId(ChatMessage entity) {
        return null;
    }
}
