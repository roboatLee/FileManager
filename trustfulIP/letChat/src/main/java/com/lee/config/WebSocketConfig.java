package com.lee.config;

import com.lee.websocket.ChatHandler;
import com.lee.websocket.JwtHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author KitenLee
 * * @date 2026/2/28
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;
    private final JwtHandshakeInterceptor jwtInterceptor;

    public WebSocketConfig(ChatHandler chatHandler,
                           JwtHandshakeInterceptor jwtInterceptor) {
        this.chatHandler = chatHandler;
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/chat")
                .addInterceptors(jwtInterceptor)   // 🔥 加上JWT拦截器
                .setAllowedOrigins("*");
    }
}