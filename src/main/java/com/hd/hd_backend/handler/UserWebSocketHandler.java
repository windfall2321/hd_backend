package com.hd.hd_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.dto.*;
import com.hd.hd_backend.entity.*;
import com.hd.hd_backend.mapper.*;
import com.hd.hd_backend.service.*;
import com.hd.hd_backend.utils.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
public class UserWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private FoodMapper foodMapper;
    // 错误映射
    private static final Map<String, String> errorMapping = new HashMap<>();
    static {
        errorMapping.put("手机号不能为空", "{\"error_code\":400,\"error_message\":\"手机号不能为空\"}");
        errorMapping.put("密码不能为空", "{\"error_code\":400,\"error_message\":\"密码不能为空\"}");
        errorMapping.put("用户不存在", "{\"error_code\":404,\"error_message\":\"用户不存在\"}");
        errorMapping.put("密码错误", "{\"error_code\":401,\"error_message\":\"密码错误\"}");
        errorMapping.put("账号已被封禁", "{\"error_code\":403,\"error_message\":\"账号已被封禁\"}");
    }
    private final ObjectMapper objectMapper = new ObjectMapper(); // 创建 ObjectMapper 实例

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        System.out.println("文本消息"); // 输出解析后的用户信息
        String payload = message.getPayload();
        // 解析消息并执行相应的操作
        // 假设消息格式为 "action:payload"
        String[] parts = payload.split(":", 2); // 限制分割为 2 部分
        String action = parts[0];
        UserDTO userDTO;

        switch (action) {
            case "register":
                userDTO = parseUserDTO(parts[1]);
                if (userDTO != null) {
                    System.out.println("解析后的用户信息: " + userDTO); // 输出解析后的用户信息
                    try {
                        NormalUser user = userService.register(userDTO);



                        String successMessage = String.format(
                                "{\"status\":200,\"message\":\"注册成功！用户为%s\"}",
                                UserToJson(user)
                        );
                        session.sendMessage(new TextMessage(successMessage));

                        WebSocketSessionManager.addSession(user.getUserId(), session);
                        System.out.println(user.getUserId()); // 输出
                    } catch (Exception e) {
                        switch (e.getMessage()){
                            case "用户名已存在":
                                session.sendMessage(new TextMessage("{\"error_code\":\"1\",\"error_message\":\""+e.getMessage()+"\"}"));
                                break;
                                default:
                                    session.sendMessage(new TextMessage("{\"error_code\":\"100\",\"error_message\":\""+e.getMessage()+"\"}"));



                        }
                    }

                } else {
                    session.sendMessage(new TextMessage("解析用户信息失败"));
                }
                break;
            case "login":
                userDTO = parseUserDTO(parts[1]);
                if (userDTO != null) {
                    System.out.println("解析后的用户信息: " + userDTO); // 输出解析后的用户信息
                    try {
                        NormalUser user = userService.login(userDTO);
                        session.sendMessage(new TextMessage(UserToJson(user)) );
                        WebSocketSessionManager.addSession(user.getUserId(), session);
                        System.out.println(user.getUserId()); // 输出
                    }catch (Exception e) {
                        // 根据异常消息返回相应的错误码和错误信息
                        String errorResponse = handleLoginError(e.getMessage());
                        session.sendMessage(new TextMessage(errorResponse));
                    }
                                    } else {
                    session.sendMessage(new TextMessage("{\"error_code\":\"1\",\"error_message\":\"数据格式错误\"}"));
                }
                break;
            case "getAllFood":
                List<FoodItem> allFood = foodMapper.findAll();
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(allFood)));
                allFood.get(1).getFoodid();
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
    private UserDTO parseUserDTO(String data) {
        try {
            UserDTO userDTO = objectMapper.readValue(data, UserDTO.class); // 使用 ObjectMapper 解析 JSON
            System.out.println("解析后的 JSON: " + objectMapper.writeValueAsString(userDTO)); // 输出解析后的 JSON
            return userDTO;
        } catch (Exception e) {
            // 处理解析异常
            e.printStackTrace(); // 输出异常信息
            return null; // 或者抛出自定义异常
        }
    }

    private String UserToJson(NormalUser user) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(user);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    private String handleLoginError(String errorMessage) {
        // 从映射中获取错误响应
        return errorMapping.getOrDefault(errorMessage, "{\"error_code\":500,\"error_message\":\"登录失败\"}");
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Integer userId = getUserIdFromSession(session);
        if (userId != null) {
            WebSocketSessionManager.removeSession(userId);
        }
        System.out.println("WebSocket连接已关闭: " + session.getId());
    }

    private Integer getUserIdFromSession(WebSocketSession session) {
        // 这里需要实现一个方法来根据session找到对应的userId
        // 这里只是一个示例实现，实际应用中需要实现一个更可靠的方法
        return Integer.parseInt(session.getId());
    }
}
