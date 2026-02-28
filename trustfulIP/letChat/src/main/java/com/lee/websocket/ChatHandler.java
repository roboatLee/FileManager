package com.lee.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.dao.ChatMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
public class ChatHandler extends TextWebSocketHandler  {
    private final ConcurrentHashMap<String, WebSocketSession> sessions
            = new ConcurrentHashMap<>();


    private static final ObjectMapper mapper
            = new ObjectMapper();

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
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message) throws IOException {
        String text = message.getPayload();
        String userName =
                (String) session.getAttributes().get("userName");
        String result = userName + ": " + text;
        System.out.println("收到消息: " + result);


        ChatMessage chat = new ChatMessage();
        chat.setType("chat");
        chat.setSender(userName);
        chat.setContent(text);
        chat.setTime(System.currentTimeMillis());
        String json = mapper.writeValueAsString(chat);

        for (WebSocketSession s : sessions.values()) {
            s.sendMessage(new TextMessage(json));
        }

    }
    @Override
    public void afterConnectionClosed(
            WebSocketSession session,
            CloseStatus status) {
        String userName =
                (String) session.getAttributes().get("userName");
        sessions.remove(userName);
        System.out.println("用户离开: " + userName);
        System.out.println("当前在线人数: " + sessions.size());
    }

}

