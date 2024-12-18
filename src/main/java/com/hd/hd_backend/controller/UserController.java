package com.hd.hd_backend.controller;

import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.entity.FoodItem;
import com.hd.hd_backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户管理", description = "用户相关接口")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            NormalUser user = userService.register(userDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "用户登录", description = "使用手机号和密码登录")
    @ApiResponse(responseCode = "200", description = "登录成功")
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @Parameter(description = "登录信息（需要手机号和密码）") 
            @RequestBody UserDTO userDTO) {
        try {
            NormalUser user = userService.login(userDTO);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(
            @PathVariable String userId,
            @RequestBody NormalUser userInfo) {
        try {
            NormalUser user = NormalUser.getUserById(userId);
            user.changeInfo(userInfo.getName(), userInfo.getAge(), 
                          userInfo.getWeight(), userInfo.getHeight(), 
                          userInfo.getPhone());
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "添加食物记录")
    @PostMapping("/{userId}/food-records")
    public ResponseEntity<?> addFoodRecord(
            @PathVariable String userId,
            @RequestBody FoodItem foodItem) {
        try {
            NormalUser user = NormalUser.getUserById(userId);
            boolean success = user.addFoodRecord(foodItem);
            return ResponseEntity.ok(success);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "查看食物记录")
    @GetMapping("/{userId}/food-records")
    public ResponseEntity<?> viewFoodRecords(@PathVariable String userId) {
        try {
            NormalUser user = NormalUser.getUserById(userId);
            return ResponseEntity.ok(user.viewFoodRecords());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 其他接口方法...
} 