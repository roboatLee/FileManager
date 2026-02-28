package com.lee.websocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */
public class ChatHandler extends TextWebSocketHandler  {
    private static List<WebSocketSession> sessions = new ArrayList<>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // 从URL获取参数
        String query = session.getUri().getQuery();

        String userName = "匿名";

        if(query != null && query.startsWith("user=")){
            userName = query.substring(5);
        }

        // 存入session属性
        session.getAttributes().put("userName",userName);
        System.out.println("用户连接: " + userName);


        System.out.println("用户已连接");
        sessions.add(session);
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

        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage(result));
        }

    }

}

