package com.hd.hd_backend.handler;

import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
@Component
public class UserWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private UserService userService;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        // 解析消息并执行相应的操作
        // 假设消息格式为 "action:payload"
        String[] parts = payload.split(":");
        String action = parts[0];
        switch (action) {
            case "register":
                UserDTO userDTO = parseUserDTO(parts[1]);
                NormalUser user = userService.register(userDTO);
                session.sendMessage(new TextMessage("注册成功: " + user.getName()));
                break;
            case "login":
                userDTO = parseUserDTO(parts[1]);
                user = userService.login(userDTO);
                session.sendMessage(new TextMessage("登录成功: " + user.getName()));
                break;
            default:
                session.sendMessage(new TextMessage("未知操作"));
        }
    }

    private UserDTO parseUserDTO(String data) {
        // 解析 UserDTO 的逻辑
        // 这里需要根据实际情况实现
        return new UserDTO(); // 返回解析后的 UserDTO
    }
}

