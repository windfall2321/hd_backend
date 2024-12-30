package com.hd.hd_backend.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.*;
import com.hd.hd_backend.mapper.FoodMapper;
import com.hd.hd_backend.mapper.FoodRecordMapper;
import com.hd.hd_backend.service.*;
import org.mockito.ArgumentMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class UserWebSocketHandlerTest {

    private UserService userService;
    private ExerciseService exerciseService;
    private FoodMapper foodMapper;
    private FoodRecordMapper foodRecordMapper;
    private WebSocketSession session;
    private UserWebSocketHandler handler;
    private ObjectMapper objectMapper;
    private Map<String, Object> sessionAttributes;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        exerciseService = mock(ExerciseService.class);
        foodMapper = mock(FoodMapper.class);
        foodRecordMapper = mock(FoodRecordMapper.class);
        session = mock(WebSocketSession.class);
        objectMapper = new ObjectMapper();
        
        handler = new UserWebSocketHandler();
        ReflectionTestUtils.setField(handler, "userService", userService);
        ReflectionTestUtils.setField(handler, "exerciseService", exerciseService);
        ReflectionTestUtils.setField(handler, "foodMapper", foodMapper);
        ReflectionTestUtils.setField(handler, "foodRecordMapper", foodRecordMapper);
        
        sessionAttributes = new HashMap<>();
        when(session.getAttributes()).thenReturn(sessionAttributes);
    }

    private ArgumentMatcher<TextMessage> messageContains(String... contents) {
        return message -> {
            String payload = message.getPayload();
            for (String content : contents) {
                if (!payload.contains(content)) {
                    return false;
                }
            }
            return true;
        };
    }

    @Test
    void testHandleRegister() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("test");
        userDTO.setPassword("password");
        
        NormalUser user = new NormalUser();
        user.setUserId(1);
        user.setName("test");
        
        when(userService.register(any(UserDTO.class))).thenReturn(user);
        
        String message = "register:" + objectMapper.writeValueAsString(userDTO);
        handler.handleTextMessage(session, new TextMessage(message));
        
        verify(session).sendMessage(argThat(messageContains(
            "\"status\":200",
            "\"message\":\"注册成功！用户为",
            "\"userId\":1",
            "\"name\":\"test\""
        )));
        
        verify(session).getAttributes();
        verify(userService).register(any(UserDTO.class));
    }

    @Test
    void testHandleLogin() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("test");
        userDTO.setPassword("password");
        
        NormalUser user = new NormalUser();
        user.setUserId(1);
        user.setName("test");
        
        when(userService.login(any(UserDTO.class))).thenReturn(user);
        
        String message = "login:" + objectMapper.writeValueAsString(userDTO);
        handler.handleTextMessage(session, new TextMessage(message));
        
        verify(session).sendMessage(any(TextMessage.class));
    }

    @Test
    void testHandleGetAllFood() throws Exception {
        List<FoodItem> foodItems = Arrays.asList(
            new FoodItem(1, "苹果", 52),
            new FoodItem(2, "香蕉", 89)
        );
        
        when(foodMapper.findAll()).thenReturn(foodItems);
        
        handler.handleTextMessage(session, new TextMessage("getAllFood"));
        
        verify(session).sendMessage(any(TextMessage.class));
        verify(foodMapper).findAll();
    }

    @Test
    void testHandleGetFoodByName() throws Exception {
        FoodItem foodItem = new FoodItem();
        foodItem.setFoodid(1);
        foodItem.setName("苹果");
        foodItem.setCalories(52);
        
        when(foodMapper.findByName("苹果")).thenReturn(foodItem);
        
        handler.handleTextMessage(session, new TextMessage("getFoodByName:苹果"));
        
        verify(session).sendMessage(any(TextMessage.class));
        verify(foodMapper).findByName("苹果");
    }

    @Test
    void testHandleAddFoodRecord() throws Exception {
        sessionAttributes.put("userId", 1);
        
        FoodRecord foodRecord = new FoodRecord();
        foodRecord.setUserId(1);
        foodRecord.setFoodId(1);
        
        String message = "addFoodRecord:" + objectMapper.writeValueAsString(foodRecord);
        
        handler.handleTextMessage(session, new TextMessage(message));
        
        verify(session).sendMessage(argThat(messageContains(
            "\"status\":200",
            "\"message\":\"食物记录添加成功\""
        )));
        verify(foodRecordMapper).insert(any(FoodRecord.class));
    }


    // ... 其他测试方法
} 