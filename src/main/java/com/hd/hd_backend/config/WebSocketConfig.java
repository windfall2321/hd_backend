package com.hd.hd_backend.config;
import com.hd.hd_backend.handler.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final WebSocketHandler webSocketHandler;
//    private final AdminWebSocketHandler adminWebSocketHandler;

    public WebSocketConfig(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
//        this.adminWebSocketHandler = adminWebSocketHandler;

    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/websocket")
                .setAllowedOrigins("*");
//        registry.addHandler(adminWebSocketHandler, "/websocket")
//                .setAllowedOrigins("*");

    }


    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 设置消息最大长度为 8MB (8 * 1024 * 1024)
        container.setMaxTextMessageBufferSize(8388608);
        container.setMaxBinaryMessageBufferSize(8388608);
        return container;
    }
}
