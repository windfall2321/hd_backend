package com.hd.hd_backend.config;
import com.hd.hd_backend.handler.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final UserWebSocketHandler userWebSocketHandler;


    public WebSocketConfig(UserWebSocketHandler userWebSocketHandler) {
        this.userWebSocketHandler = userWebSocketHandler;


    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userWebSocketHandler, "/websocket")
                .setAllowedOrigins("*");


    }
}
