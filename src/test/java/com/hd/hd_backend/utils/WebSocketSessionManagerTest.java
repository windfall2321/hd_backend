package com.hd.hd_backend.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WebSocketSessionManagerTest {

    private WebSocketSession mockSession;
    private final Integer userId = 1;

    @BeforeEach
    void setUp() {
        // 初始化mock
        MockitoAnnotations.openMocks(this);
        mockSession = mock(WebSocketSession.class);
        // 清理之前的会话
        WebSocketSessionManager.removeSession(userId);
    }

    @Test
    void testAddAndGetSession() {
        WebSocketSessionManager.addSession(userId, mockSession);
        assertEquals(mockSession, WebSocketSessionManager.getSession(userId));
    }

    @Test
    void testRemoveSession() {
        WebSocketSessionManager.addSession(userId, mockSession);
        WebSocketSessionManager.removeSession(userId);
        assertNull(WebSocketSessionManager.getSession(userId));
    }

    @Test
    void testGetNonExistentSession() {
        assertNull(WebSocketSessionManager.getSession(999));
    }

    @Test
    void testAddMultipleSessions() {
        WebSocketSession mockSession2 = mock(WebSocketSession.class);
        
        WebSocketSessionManager.addSession(1, mockSession);
        WebSocketSessionManager.addSession(2, mockSession2);
        
        assertEquals(mockSession, WebSocketSessionManager.getSession(1));
        assertEquals(mockSession2, WebSocketSessionManager.getSession(2));
        
        // 清理
        WebSocketSessionManager.removeSession(1);
        WebSocketSessionManager.removeSession(2);
    }

    @Test
    void testConcurrentAccess() throws InterruptedException {
        int numThreads = 5;
        Thread[] threads = new Thread[numThreads];
        
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                WebSocketSession session = mock(WebSocketSession.class);
                WebSocketSessionManager.addSession(threadId, session);
                assertEquals(session, WebSocketSessionManager.getSession(threadId));
                WebSocketSessionManager.removeSession(threadId);
            });
        }
        
        // 启动所有线程
        for (Thread thread : threads) {
            thread.start();
        }
        
        // 等待所有线程完成
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    void testReplaceExistingSession() {
        WebSocketSession newSession = mock(WebSocketSession.class);
        
        // 添加第一个会话
        WebSocketSessionManager.addSession(userId, mockSession);
        assertEquals(mockSession, WebSocketSessionManager.getSession(userId));
        
        // 替换为新会话
        WebSocketSessionManager.addSession(userId, newSession);
        assertEquals(newSession, WebSocketSessionManager.getSession(userId));
        
        // 清理
        WebSocketSessionManager.removeSession(userId);
    }
} 