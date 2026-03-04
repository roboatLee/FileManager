package com.lee.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.dao.ChatMessage;
import com.lee.dao.WsMessage;
import com.lee.persistence.json.JsonFileStore;
import com.lee.service.ChatMessageRepository;
import com.lee.service.repository.ChatMessageRepositoryImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
@Component
public class ChatHandler extends TextWebSocketHandler  {
    private final ConcurrentHashMap<String, Set<WebSocketSession>> sessions
            = new ConcurrentHashMap<>();

    private final ChatMessageRepository repository;

    public ChatHandler(ChatMessageRepository repository) {
        this.repository = repository;
    }
    private static final String SESSION_USER_KEY = "username";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        String userName =
                (String) session.getAttributes().get(SESSION_USER_KEY);

        System.out.println("用户连接: " + userName);

        Set<WebSocketSession> userSessions =
                sessions.computeIfAbsent(userName,
                        k -> ConcurrentHashMap.newKeySet());

        boolean firstConnection = userSessions.isEmpty();

        userSessions.add(session);

        System.out.println("当前在线人数: " + sessions.size());

        if (firstConnection) {
            broadcastJoin(userName);
            broadcastUsers();
        }

        sendHistory(session);
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
                (String) session.getAttributes().get(SESSION_USER_KEY);

        Set<WebSocketSession> userSessions = sessions.get(userName);

        if (userSessions != null) {

            userSessions.remove(session);

            if (userSessions.isEmpty()) {
                sessions.remove(userName);
                broadcastLeave(userName);
            }
        }

        System.out.println("用户离开: " + userName);
        System.out.println("当前在线人数: " + sessions.size());

        broadcastUsers();
    }

    private void sendHistory(WebSocketSession session) {
        try {

            String userName =
                    (String) session.getAttributes().get(SESSION_USER_KEY);

            List<ChatMessage> list = repository.getMessages();

            // 只发送和当前用户有关的聊天记录
            List<ChatMessage> related = list.stream()
                    .filter(m -> {
                        String convId = m.getConversationId();

                        if ("global".equals(convId)) {
                            return true;
                        }

                        return convId.contains(userName);
                    })
                    .collect(Collectors.toList());

            send(session, "history", related);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleChat(WebSocketSession session, WsMessage ws)
            throws Exception {

        String userName =
                (String) session.getAttributes().get(SESSION_USER_KEY);

        Map data = (Map) ws.getData();
        String content = (String) data.get("content");

        String toUser = (String) data.get("toUser");
        String conversationId;
        if (toUser != null && !"".equals(toUser)) {

            conversationId = buildPrivateConversationId(userName, toUser);

        } else {
            conversationId = "global";
        }

        ChatMessage chat = new ChatMessage();

        chat.setSender(userName);
        chat.setContent(content);
        chat.setTime(System.currentTimeMillis());
        chat.setConversationId(conversationId);

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
        String convId = chat.getConversationId();

        // 全局聊天室
        if ("global".equals(convId)) {

            for (Set<WebSocketSession> userSessions : sessions.values()) {
                for (WebSocketSession s : userSessions) {
                    if (s.isOpen()) {
                        send(s, "chat", chat);
                    }
                }
            }
            return;
        }

        // 私聊：格式约定  userA_userB
        String[] users = convId.split("_");

        for (String user : users) {

            Set<WebSocketSession> targetSessions = sessions.get(user);

            if (targetSessions != null) {
                for (WebSocketSession s : targetSessions) {
                    if (s.isOpen()) {
                        send(s, "chat", chat);
                    }
                }
            }

        }


    }

    private void broadcastJoin(String userName) {

        try {

            Map<String,String> data = new HashMap<>();
            data.put("user", userName);

            for (Set<WebSocketSession> userSessions : sessions.values()) {
                for (WebSocketSession s : userSessions) {
                    if (s.isOpen()) {
                        send(s,"join",data);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastLeave(String userName) {

        try {

            Map<String,String> data = new HashMap<>();
            data.put("user", userName);

            for (Set<WebSocketSession> userSessions : sessions.values()) {
                for (WebSocketSession s : userSessions) {
                    if (s.isOpen()) {
                        send(s,"leave",data);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcastUsers() {

        try {

            List<String> userList =
                    new ArrayList<>(sessions.keySet());

            for (Set<WebSocketSession> userSessions : sessions.values()) {
                for (WebSocketSession s : userSessions) {
                    if (s.isOpen()) {
                        send(s, "users", userList);
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String buildPrivateConversationId(String user1, String user2) {

        if (user1.compareTo(user2) < 0) {
            return user1 + "_" + user2;
        } else {
            return user2 + "_" + user1;
        }
    }
}

