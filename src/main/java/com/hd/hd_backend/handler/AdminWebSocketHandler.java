package com.hd.hd_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.*;
import com.hd.hd_backend.mapper.FoodMapper;
import com.hd.hd_backend.service.AdminService;
import com.hd.hd_backend.service.ExerciseService;
import com.hd.hd_backend.service.UserService;
import com.hd.hd_backend.utils.JsonUtils;
import com.hd.hd_backend.utils.WebSocketSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AdminWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private ExerciseService exerciseService;
    @Autowired
    private FoodMapper foodMapper;
    @Autowired
    private AdminService adminService;
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

        switch (action) {
            case "admin-login":
                if (session.getAttributes().containsKey("userid")){
                    session.sendMessage(new TextMessage("{\"error_code\":\"400\",\"error_message\":\"管理员已登录\"}"));
                    break;
                }
                try{
                    Administrator administrator=JsonUtils.fromJson(parts[1], Administrator.class);
                    Integer id=adminService.login(administrator);
                    session.getAttributes().put("userid", id);
                    session.sendMessage(new TextMessage("{\"status\":\"200\",\"error_message\":\"登录成功\"}"));

                }catch (Exception e)
                {
                    session.sendMessage(new TextMessage("{\"error_code\":\"401\",\"error_message\":\""+e.getMessage()+"\"}"));
                }

                break;
            case "admin-addProfileImage":
                break;
            case "admin-blockUser":
                if (!session.getAttributes().containsKey("userid")){
                    session.sendMessage(new TextMessage("{\"error_code\":\"400\",\"error_message\":\"管理员未登录\"}"));
                    break;
                }
                try{
                    NormalUser normalUser=JsonUtils.fromJson(parts[1], NormalUser.class);
                    adminService.blockUser(normalUser);
                    WebSocketSession user_session=WebSocketSessionManager.getSession(normalUser.getUserId());
                    if (user_session!=null){
                        user_session.sendMessage(new TextMessage("{\"error_code\":\"110\",\"error_message\":\"你已被封禁\"}"));
                        session.sendMessage(new TextMessage("用户在线"));
                    }
                    else{
                        session.sendMessage(new TextMessage("用户未在线"));
                    }



                }
                catch (Exception e){
                    session.sendMessage(new TextMessage("{\"error_code\":\"401\",\"error_message\":\""+e.getMessage()+"\"}"));
                }

            default:
                session.sendMessage(new TextMessage("{\"error_code\":\"400\",\"error_message\":\"格式错误\"}"));
        }
    }

    private String handleLoginError(String errorMessage) {
        // 从映射中获取错误响应
        return errorMapping.getOrDefault(errorMessage, "{\"error_code\":500,\"error_message\":\"登录失败\"}");
    }

}