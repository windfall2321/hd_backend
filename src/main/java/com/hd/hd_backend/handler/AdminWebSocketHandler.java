package com.hd.hd_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.entity.*;
import com.hd.hd_backend.service.AdminService;
import com.hd.hd_backend.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

@Component
public class AdminWebSocketHandler extends TextWebSocketHandler {
    @Autowired
    private AdminService adminService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String[] parts = payload.split(":", 2);
        String action = parts[0];

        switch (action) {
            case "login":
                Administrator loginAdmin = JsonUtils.fromJson(parts[1], Administrator.class);
                try {
                    Administrator admin = adminService.login(loginAdmin);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.ADMIN_LOGIN_SUCCESS.ordinal(),
                        admin,
                        "admin"
                    )));
                    WebSocketSessionManager.addSession(admin.getId(), session);
                    session.getAttributes().put("adminId", admin.getId());
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.ADMIN_LOGIN_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "updateAdmin":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.ADMIN_UPDATE_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    Administrator updateInfo = objectMapper.readValue(parts[1], Administrator.class);
                    Integer adminId = (Integer) session.getAttributes().get("adminId");
                    adminService.updateAdmin(adminId, updateInfo);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.ADMIN_UPDATE_SUCCESS.ordinal(),
                        "更新成功",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.ADMIN_UPDATE_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "getAllUsers":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.GET_ALL_USERS_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    List<NormalUser> users = adminService.getAllUsers();
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.GET_ALL_USERS_SUCCESS.ordinal(),
                        users,
                        "data"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.GET_ALL_USERS_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "blockUser":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.BLOCK_USER_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    Integer userId = Integer.parseInt(parts[1]);
                    adminService.blockUser(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.BLOCK_USER_SUCCESS.ordinal(),
                        "封禁成功",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.BLOCK_USER_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "unblockUser":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNBLOCK_USER_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    Integer userId = Integer.parseInt(parts[1]);
                    adminService.unblockUser(userId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNBLOCK_USER_SUCCESS.ordinal(),
                        "解封成功",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNBLOCK_USER_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "offendPost":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.OFFEND_POST_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    Integer postId = Integer.parseInt(parts[1]);
                    adminService.offendPost(postId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.OFFEND_POST_SUCCESS.ordinal(),
                        "帖子已标记违规",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.OFFEND_POST_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "unoffendPost":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNOFFEND_POST_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    Integer postId = Integer.parseInt(parts[1]);
                    adminService.unoffendPost(postId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNOFFEND_POST_SUCCESS.ordinal(),
                        "帖子已解封",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNOFFEND_POST_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "offendComment":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.OFFEND_COMMENT_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    Integer commentId = Integer.parseInt(parts[1]);
                    adminService.offendComment(commentId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.OFFEND_COMMENT_SUCCESS.ordinal(),
                        "评论已标记违规",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.OFFEND_COMMENT_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            case "unoffendComment":
                if (!session.getAttributes().containsKey("adminId")) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNOFFEND_COMMENT_FAIL.ordinal(),
                        "管理员未登录",
                        "error_message"
                    )));
                    break;
                }
                try {
                    Integer commentId = Integer.parseInt(parts[1]);
                    adminService.unoffendComment(commentId);
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNOFFEND_COMMENT_SUCCESS.ordinal(),
                        "评论已解封",
                        "message"
                    )));
                } catch (Exception e) {
                    session.sendMessage(new TextMessage(JsonUtils.toJsonMsg(
                        WebSocketCode.UNOFFEND_COMMENT_FAIL.ordinal(),
                        e.getMessage(),
                        "error_message"
                    )));
                }
                break;

            default:
                session.sendMessage(new TextMessage("未知操作"));
        }
    }
}