package com.lee.websocket;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lee.dao.ChatMessage;
import com.lee.dao.WsMessage;
import com.lee.entity.User;
import com.lee.service.ChatMessageRepository;
import com.lee.service.IUserService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
@Component
public class ChatHandler extends TextWebSocketHandler  {
    private final ConcurrentHashMap<Long, Set<WebSocketSession>> sessions
            = new ConcurrentHashMap<>();

    private final ChatMessageRepository repository;
    private IUserService userService;

    public ChatHandler(ChatMessageRepository repository, IUserService userService) {
        this.repository = repository;
        this.userService = userService;
    }
    private static final String SESSION_USER_KEY = "userId";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        //得到连进去之后用户的名字
        Long userId = (Long) session.getAttributes().get(SESSION_USER_KEY);
        /**
         * 它尝试从 sessions 中获取 userName 的键值。
         * 如果这个键不存在，那么就使用提供的 lambda 表达式来创建一个新的 Set（在这种情况下，是 ConcurrentHashMap.newKeySet()，这将返回一个新的线程安全的 Set）
         * 并将其存储到 sessions map 中。
         * */
        Set<WebSocketSession> userSessions =
                sessions.computeIfAbsent(userId,
                        k -> ConcurrentHashMap.newKeySet());
        /**
         * 是否是第一次连接
         * 如果是第一次连接，那么就广播这个用户加入了聊天程序
         * 如果不是，那么就不广播
         * */
        boolean firstConnection = userSessions.isEmpty();

        if (firstConnection) {
            broadcastJoin(userId);
            broadcastUsers();
        }
        userSessions.add(session);
        sendHistory(session);
    }

    /**
     *
     * TextMessage类型
     * {
     *     type: "chat",
     *     data: {
     *       content,
     *       toUser
     *     }
     *   }
     * */
    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message) {


        try {
            //将前端信息转换成ws对象
            WsMessage<Map<String,String>> ws =
                    objectMapper.readValue(
                        message.getPayload(),
                        new TypeReference<
                                WsMessage<Map<String,String>>
                                >(){}
                    );

            //如果发送的信息是chat类型，那就处理该消息
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

        Long userId = (Long) session.getAttributes().get(SESSION_USER_KEY);
        Set<WebSocketSession> userSessions = sessions.get(userId);


        User user = userService.getById(userId);
        String userName = user.getUsername();
        System.out.println(userName);


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

            Long userId = (Long) session.getAttributes().get(SESSION_USER_KEY);
            User user = userService.getById(userId);

            List<ChatMessage> list = repository.getMessages();

            // 只发送和当前用户有关的聊天记录
            List<ChatMessage> related = list.stream()
                    .filter(m -> {
                        String convId = m.getConversationId();

                        if ("global".equals(convId)) {
                            return true;
                        }
                        return convId.contains(user.getUsername());
                    })
                    .collect(Collectors.toList());

            send(session, "history", related);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理消息
     *判断消息是私聊还是群聊，然后进行存储
     * */
    private void handleChat(WebSocketSession session, WsMessage ws)
            throws Exception {

        Long userId =
                (Long) session.getAttributes().get(SESSION_USER_KEY);

        Map data = (Map) ws.getData();

        String content = (String) data.get("content");
        String toUser = (String) data.get("toUser");
        String conversationId;

        /**
         * 如果toUser不是空且也不是空字符串，那么就是私聊，设置PrivateConversationId
         * 否则，就是群聊
         * */
        if (toUser != null && !"".equals(toUser)) {
            Long toUserId = Long.valueOf(toUser);
            conversationId = buildPrivateConversationId(userId, toUserId);
        } else {
            conversationId = "global";
        }

        /**
         * 设置聊天信息
         * */
        ChatMessage chat = new ChatMessage();
        chat.setSender(userId);
        chat.setContent(content);
        chat.setTime(System.currentTimeMillis());
        chat.setConversationId(conversationId);

        //存储消息
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

    /**
     * 广播谁加入了
     * */
    private void broadcastJoin(Long userName) {

        try {

            Map<String,Long> data = new HashMap<>();
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
    /**
     * 广播谁已经离开了
     * */
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

    /**
     * 在线用户列表
     * */
    private void broadcastUsers() {
        try {
            List<Long> userList =
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

    /**
     * 用户私聊
     * */
    private String buildPrivateConversationId(Long user1, Long user2) {

        if (user1 < user2) {
            return user1 + "_" + user2;
        } else {
            return user2 + "_" + user1;
        }
    }
}

