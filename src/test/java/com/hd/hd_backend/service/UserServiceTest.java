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

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO();
        userDTO.setName("testUser");
        userDTO.setPassword("password123");
        userDTO.setPhone("13800138000");
        userDTO.setAge(25);
        userDTO.setHeight(175);
        userDTO.setWeight(70);

        normalUser = new NormalUser();
        normalUser.setName("testUser");
        normalUser.setPassword("password123");
        normalUser.setPhone("13800138000");
        normalUser.setAge(25);
        normalUser.setHeight(175);
        normalUser.setWeight(70);
        normalUser.setUserId(1);
    }

    @Test
    void testRegisterSuccess() throws Exception {
        when(userMapper.findByName(anyString())).thenReturn(null);
        when(userMapper.findByPhone(anyString())).thenReturn(null);
        
        NormalUser result = userService.register(userDTO);
        
        assertNotNull(result);
        assertEquals(userDTO.getName(), result.getName());
        verify(userMapper).insert(any(NormalUser.class));
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
        when(userMapper.findByName("testUser")).thenReturn(normalUser);
        userDTO.setLogin(true);
        
        NormalUser result = userService.login(userDTO);
        
        assertNotNull(result);
        assertEquals(userDTO.getName(), result.getName());
    }

    @Test
    void testLoginWithWrongPassword() {
        when(userMapper.findByName("testUser")).thenReturn(normalUser);
        userDTO.setPassword("wrongPassword");
        userDTO.setLogin(true);
        
        Exception exception = assertThrows(Exception.class, () -> {
            userService.login(userDTO);
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