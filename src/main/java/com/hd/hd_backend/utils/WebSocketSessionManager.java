package com.hd.hd_backend.utils;

import org.springframework.web.socket.WebSocketSession;

import java.util.concurrent.ConcurrentHashMap;
public class WebSocketSessionManager {
    private static final ConcurrentHashMap<Integer, WebSocketSession> USER_SESSIONS = new ConcurrentHashMap<>();

    public static void addSession(Integer userId, WebSocketSession session) {
        USER_SESSIONS.put(userId, session);
    }

    public static WebSocketSession getSession(Integer userId) {
        return USER_SESSIONS.get(userId);
    }

    public static void removeSession(Integer userId) {
        USER_SESSIONS.remove(userId);
    }
}