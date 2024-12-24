package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    private UserDTO userDTO;
    private NormalUser normalUser;
    private NormalUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new NormalUser();
        testUser.setName("testUser");
        testUser.setPassword("password123");
        testUser.setWeight(70);
        testUser.setAge(25);
        testUser.setHeight(175);
        testUser.setPhone("13800138000");
        
        // 设置模拟行为
        when(userMapper.findByPhone("13800138000")).thenReturn(testUser);
        when(userMapper.insert(any())).thenReturn(1);  // 使用any()而不是any(NormalUser.class)
    }

    @Test
    void testRegisterSuccess() throws Exception {
        // 准备测试数据
        UserDTO userDTO = new UserDTO();
        userDTO.setName("newUser");
        userDTO.setPassword("password123");
        userDTO.setPhone("13800138001");
        userDTO.setAge(25);
        userDTO.setHeight(175);
        userDTO.setWeight(70);
        
        // 设置 mock 行为
        when(userMapper.findByName(anyString())).thenReturn(null);
        when(userMapper.findByPhone(anyString())).thenReturn(null);
        
        NormalUser mockUser = new NormalUser();
        mockUser.setUserId(1);
        mockUser.setName(userDTO.getName());
        mockUser.setPassword(userDTO.getPassword());
        mockUser.setPhone(userDTO.getPhone());
        
        when(userMapper.findByPhone(userDTO.getPhone())).thenReturn(mockUser);
        when(userMapper.insert(any())).thenReturn(1);  // 使用any()而不是any(NormalUser.class)
        
        // 执行测试
        NormalUser result = userService.register(userDTO);
        
        // 验证结果
        assertNotNull(result);
        assertEquals(userDTO.getName(), result.getName());
        verify(userMapper).insert(any());  // 使用any()而不是any(NormalUser.class)
    }

    @Test
    void testRegisterWithExistingUsername() {
        when(userMapper.findByName("testUser")).thenReturn(normalUser);
        
        Exception exception = assertThrows(Exception.class, () -> {
            userService.register(userDTO);
        });
        
        assertEquals("用户名已存在", exception.getMessage());
    }

    @Test
    void testLoginSuccess() throws Exception {
        // 创建登录用的UserDTO
        UserDTO loginDTO = new UserDTO();
        loginDTO.setPhone("13800138000");
        loginDTO.setPassword("password123");
        
        NormalUser result = userService.login(loginDTO);
        
        assertNotNull(result);
        assertEquals("testUser", result.getName());
    }

    @Test
    void testLoginWithWrongPassword() {
        // 创建登录用的UserDTO
        UserDTO loginDTO = new UserDTO();
        loginDTO.setPhone("13800138000");
        loginDTO.setPassword("wrongPassword");
        
        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(loginDTO);
        });
        assertEquals("密码错误", exception.getMessage());
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userMapper.findById(1)).thenReturn(normalUser);
        
        NormalUser updateInfo = new NormalUser();
        updateInfo.setName("updatedName");
        updateInfo.setAge(26);
        
        userService.updateUser(1, updateInfo);
        
        verify(userMapper).update(any(NormalUser.class));
    }
} 