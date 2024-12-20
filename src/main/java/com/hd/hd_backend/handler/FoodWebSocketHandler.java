package com.hd.hd_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.entity.FoodItem;
import com.hd.hd_backend.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.List;



@Component
public class FoodWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String[] parts = payload.split(":", 2);
        String action = parts[0];

        switch (action) {
            case "getAllFood":
                List<FoodItem> allFood = foodMapper.findAll();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(allFood)));
                break;
            case "getFoodByName":
                if (parts.length > 1) {
                    FoodItem food = foodMapper.findByName(parts[1]);
                    if (food != null) {
                        session.sendMessage(new TextMessage(food.NutritionalDetails()));
                    } else {
                        session.sendMessage(new TextMessage("未找到该食物"));
                    }
                }
                break;
            default:
                session.sendMessage(new TextMessage("未知操作"));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("食物营养WebSocket连接已建立: " + session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session,
                                    org.springframework.web.socket.CloseStatus status) throws Exception {
        System.out.println("食物营养WebSocket连接已关闭: " + session.getId());
    }


}
