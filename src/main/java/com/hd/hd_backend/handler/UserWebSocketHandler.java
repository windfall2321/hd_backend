package com.hd.hd_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.dto.*;
import com.hd.hd_backend.entity.*;
import com.hd.hd_backend.mapper.*;
import com.hd.hd_backend.service.*;
import com.hd.hd_backend.utils.JsonUtils;
import com.hd.hd_backend.utils.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
    private ExerciseService exerciseService;
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
                        session.getAttributes().put("userId", user.getUserId());
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
                        session.getAttributes().put("userId", user.getUserId());
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
            case "getAllExerciseItem":
                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage("请先登录"));
                }
                else{

                    List<ExerciseItem> exercises= exerciseService.getAllExerciseItem();
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(exercises)));
                }
                break;
            case "addExerciseItem":
                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage("请先登录"));
                }
                else{
                    if (parts.length > 1) {
                        ExerciseItem newExerciseItem = JsonUtils.fromJson(parts[1], ExerciseItem.class);
                        exerciseService.addExerciseItem(newExerciseItem);
                        session.sendMessage(new TextMessage("success"));
                    }
                }
                break;

            case "addExerciseRecord":
                ExerciseRecord exerciseRecord = JsonUtils.fromJson(parts[1], ExerciseRecord.class);

                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage("请先登录"));
                }
                else{
                    if (parts.length > 1) {
                        int user_id= Integer.parseInt(session.getAttributes().get("userId").toString());
                        exerciseRecord.setUserId(user_id);
                        exerciseService.addExerciseRecord(exerciseRecord);
                        session.sendMessage(new TextMessage("success"));
                    }
                }
                break;

            case "getUserExerciseRecord":
                if(!session.getAttributes().containsKey("userId"))
                {
                    session.sendMessage(new TextMessage("请先登录"));
                }
                else{
                    if (parts.length > 1) {
                        int user_id= Integer.parseInt(session.getAttributes().get("userId").toString());

                        List<ExerciseRecord> exercises= exerciseService.getUserExerciseRecord(user_id);
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(exercises)));

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

}