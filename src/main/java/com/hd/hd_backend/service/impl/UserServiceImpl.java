package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.mapper.UserMapper;
import com.hd.hd_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public NormalUser register(UserDTO userDTO) throws Exception {
        // 检查用户名是否已存在
        if (userMapper.findByName(userDTO.getName()) != null) {
            throw new Exception("用户名已存在");
        }

        // 数据验证
       

        // 创建新用户
        NormalUser user = new NormalUser();
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());  // 实际应用中应该加密存储
        user.setWeight(userDTO.getWeight());
        user.setAge(userDTO.getAge());
        user.setHeight(userDTO.getHeight());
        user.setPhone(userDTO.getPhone());
        user.setIsblocked(0);  // 新用户��未被封禁
        user.setProfilePicture("https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800");
        // 保存到数据库
        userMapper.insert(user);
        NormalUser normalUser =userMapper.findByPhone(userDTO.getPhone());
        return normalUser;
    }

    @Override
    public NormalUser login(UserDTO userDTO) throws Exception {
        // 验证参数
        if (userDTO.getPhone() == null || userDTO.getPhone().trim().isEmpty()) {
            throw new Exception("手机号不能为空");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty()) {
            throw new Exception("密码不能为空");
        }


        // 通过手机号查找用户
        NormalUser user = userMapper.findByPhone(userDTO.getPhone());
        if (user == null) {
            throw new Exception("用户不存在");
        }
        
        // 验证密码
        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new Exception("密码错误");
        }
        
        // 检查账号状态
        if (user.getIsblocked() == 1) {
            throw new Exception("账号已被封禁");
        }
        
        return user;
    }

    @Override
    public void updateUser(Integer userId, NormalUser updateInfo) throws Exception {
        NormalUser user = userMapper.findById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }
        
        // 如果要更新用户名，需要检查新用户名是否已存在
        if (updateInfo.getName() != null && !updateInfo.getName().isEmpty()) {
            NormalUser existingUser = userMapper.findByName(updateInfo.getName());
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                throw new Exception("用户名已存在");
            }
        }
        
        // 如果要更新手机号，需要检查新手机号是否已存在
        if (updateInfo.getPhone() != null && !updateInfo.getPhone().isEmpty()) {
            NormalUser existingUser = userMapper.findByPhone(updateInfo.getPhone());
            if (existingUser != null && !existingUser.getUserId().equals(userId)) {
                throw new Exception("手机号已被使用");
            }
        }
        
        user.update(updateInfo);
        userMapper.update(user);
    }

    @Override
    public NormalUser getUserById(Integer userId) throws Exception {
        NormalUser user = userMapper.findById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }
        return user;
    }
} 