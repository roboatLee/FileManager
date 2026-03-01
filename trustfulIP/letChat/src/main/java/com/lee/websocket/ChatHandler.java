package com.lee.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.dao.ChatMessage;
import com.lee.dao.WsMessage;
import com.lee.persistence.json.JsonFileStore;
import com.lee.service.ChatMessageRepository;
import com.lee.service.repository.ChatMessageRepositoryImpl;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
public class ChatHandler extends TextWebSocketHandler  {
    private final ConcurrentHashMap<String, WebSocketSession> sessions
            = new ConcurrentHashMap<>();


    private final ChatMessageRepository repository =
            new ChatMessageRepositoryImpl();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        String query = session.getUri().getQuery();

        String userName = "匿名";

        if(query != null && query.startsWith("user=")){
            userName = query.substring(5);
        }

        session.getAttributes().put("userName",userName);
        System.out.println("用户连接: " + userName);
        sessions.put(userName, session);
        System.out.println("当前在线人数: " + sessions.size());

        sendHistory(session);
        broadcastJoin(userName);
        broadcastUsers();
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message) {

        try {
            WsMessage<Map<String,String>> ws =
                    objectMapper.readValue(
                        message.getPayload(),
                        new TypeReference<
                                WsMessage<Map<String,String>>
                                >(){}
                    );

            switch (ws.getType()) {
                case "chat":
                    handleChat(session, ws);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status) {
        String userName =
                (String) session.getAttributes().get("userName");
        sessions.remove(userName);
        broadcastLeave(userName);

        System.out.println("用户离开: " + userName);
        System.out.println("当前在线人数: " + sessions.size());
        broadcastUsers();

    }

    private void sendHistory(WebSocketSession session) {
        try {
            List<ChatMessage> list = repository.getMessages();
            send(session,"history",list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleChat(WebSocketSession session, WsMessage ws)
            throws Exception {

        String userName =
                (String) session.getAttributes().get("userName");

        Map data = (Map) ws.getData();

        String content = (String) data.get("content");

        ChatMessage chat = new ChatMessage();

        chat.setSender(userName);
        chat.setContent(content);
        chat.setTime(System.currentTimeMillis());

        repository.addMessage(chat);

        broadcastChat(chat);

    }

    private void send(
            WebSocketSession session,
            String type,
            Object data
    ) throws Exception {

        WsMessage ws = new WsMessage();

        ws.setType(type);
        ws.setData(data);
        ws.setTime(System.currentTimeMillis());

        String json =
                objectMapper.writeValueAsString(ws);

        session.sendMessage(
                new TextMessage(json)
        );
    }

    private void broadcastChat(ChatMessage chat)
            throws Exception {

        for (WebSocketSession s : sessions.values()) {

            send(s,"chat",chat);

        }

    }

    private void broadcastJoin(String userName) {

        try {

            Map<String,String> data = new HashMap<>();
            data.put("user", userName);

            for (WebSocketSession s : sessions.values()) {

                send(s,"join",data);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastLeave(String userName) {

        try {

            Map<String,String> data = new HashMap<>();
            data.put("user", userName);

            for (WebSocketSession s : sessions.values()) {

                send(s,"leave",data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastUsers() {

        try {

            List<String> userList =
                    new ArrayList<>(sessions.keySet());

            for (WebSocketSession s : sessions.values()) {

                send(s, "users", userList);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

