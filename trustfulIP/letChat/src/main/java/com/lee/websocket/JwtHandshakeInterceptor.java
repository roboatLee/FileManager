package com.lee.websocket;

import com.lee.security.JwtUtil;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author KitenLee
 * * @date 2026/3/3
 */
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) throws Exception {

        //如果请求(request)是一个ServletServerHttpRequest的实例，那么就执行下面的代码
        if (request instanceof ServletServerHttpRequest) {

            ServletServerHttpRequest servletRequest =
                    (ServletServerHttpRequest) request;

            System.out.println("握手开始");
            String token =
                    servletRequest.getServletRequest()
                            .getParameter("token");
            System.out.println("token = " + token);

            if (token == null) {
                return false;
            }

            if (JwtUtil.validateToken(token)) {
                Long userId =
                        JwtUtil.getUserIdInt(token);
                attributes.put("userId", userId);
                return true;
            }
        }

        return false;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ) {
    }

}