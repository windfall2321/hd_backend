package com.hd.hd_backend.config;
import com.hd.hd_backend.handler.UserWebSocketHandler; // 确保导入正确
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final UserWebSocketHandler userWebSocketHandler; // 确保处理程序不为空

    public WebSocketConfig(UserWebSocketHandler userWebSocketHandler) {
        this.userWebSocketHandler = userWebSocketHandler; // 正确注入处理程序
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册 WebSocket 的路径
        registry.addHandler(userWebSocketHandler, "/user-websocket")
                .setAllowedOrigins("*"); // 允许跨域
    }
}
