package com.lee.config;

import com.lee.websocket.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        ChatHandler handler = new ChatHandler();
        registry.addHandler(handler,"/chat")
                .setAllowedOrigins("*");


    }

}
