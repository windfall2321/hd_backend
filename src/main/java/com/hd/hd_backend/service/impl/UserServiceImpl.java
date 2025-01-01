package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.entity.User;
import com.hd.hd_backend.entity.Weight;
import com.hd.hd_backend.mapper.UserMapper;
import com.hd.hd_backend.mapper.WeightMapper;
import com.hd.hd_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    private WeightMapper weightMapper;
    @Override
    public NormalUser register(NormalUser normalUser) throws Exception {
        // 检查用户名是否已存在
//        if (userMapper.findByName(userDTO.getName()) != null) {
//            throw new Exception("用户名已存在");
//        }

        // 数据验证
       

        // 创建新用户
//        NormalUser user = new NormalUser();
//        user.setName(normalUser.getName());
//        user.setPassword(normalUser.getPassword());  // 实际应用中应该加密存储
//        user.setWeight(normalUser.getWeight());
//        user.setAge(normalUser.getAge());
//        user.setHeight(normalUser.getHeight());
//        user.setPhone(normalUser.getPhone());
//        user.setIsBlocked(0);  // 新用户��未被封禁
//        user.setProfilePicture("https://img1.baidu.com/it/u=534429813,2995452219&fm=253&fmt=auto?w=800&h=800");
//        user.setGender(normalUser.getGender());
//        user.setActivityFactor(normalUser.getActivityFactor());
        // 保存到数据库
        userMapper.insertUser(normalUser);
        int id=userMapper.findByPhone(normalUser.getPhone()).getId();
        normalUser.setId(id);
        userMapper.insertNormalUser(normalUser);
        Weight weight=new Weight();
        weight.setUserId(id);
        weightMapper.insertWeight(weight);//自动插入一个体重
        return userMapper.findById(id);
    }

    @Override
    public User login(NormalUser  normalUser) throws Exception {
        // 验证参数
        if (normalUser.getPhone() == null || normalUser.getPhone().trim().isEmpty()) {
            throw new Exception("手机号不能为空");
        }
        if (normalUser.getPassword() == null || normalUser.getPassword().trim().isEmpty()) {
            throw new Exception("密码不能为空");
        }


        // 通过手机号查找用户
        User user = userMapper.findByPhone(normalUser.getPhone());
        if (user == null) {
            throw new Exception("用户不存在");
        }

        // 验证密码
        if (!user.getPassword().equals(normalUser.getPassword())) {
            throw new Exception("密码错误");
        }

        if (user.getIsAdmin() == 0)
        {
            NormalUser normal=userMapper.findById(user.getId());
            // 检查账号状态
//            if (normal.getIsBlocked() == 1) {
//                throw new Exception("账号已被封禁");
//            }

            return normal;

        }
        else
        {
            return user;
        }


    }

    @Override
    public void updateUser(Integer userId, NormalUser updateInfo) throws Exception {
        NormalUser user = userMapper.findById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }

        // 如果要更新用户名，需要检查新用户名是否已存在
//        if (updateInfo.getName()!=null&&!(updateInfo.getName().equals(user.getName()))) {
//            User existingUser = userMapper.findByPhone(updateInfo.getName());
//            if (existingUser != null && !existingUser.getId().equals(userId)) {
//                throw new Exception("用户名已存在");
//            }
//        }

        // 如果要更新手机号，需要检查新手机号是否已存在
        if (updateInfo.getPhone() != null && !updateInfo.getPhone().isEmpty()) {
            User existingUser = userMapper.findByPhone(updateInfo.getPhone());
            if (existingUser != null && !existingUser.getId().equals(userId)) {
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