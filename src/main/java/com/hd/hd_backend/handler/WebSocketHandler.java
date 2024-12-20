package com.hd.hd_backend.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 客户端连接建立后
        System.out.println("WebSocket connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 接收到客户端消息
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // 回复客户端
        session.sendMessage(new TextMessage("Server received: " + payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 客户端连接关闭后
        System.out.println("WebSocket connection closed: " + session.getId());
    }
}
