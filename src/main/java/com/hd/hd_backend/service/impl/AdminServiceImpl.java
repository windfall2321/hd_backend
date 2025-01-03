package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.Administrator;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.mapper.AdministratorMapper;
import com.hd.hd_backend.mapper.UserMapper;
import com.hd.hd_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdministratorMapper adminMapper;
    
    @Autowired
    private UserMapper userMapper;

    @Override
    public Administrator login(Administrator admin) throws Exception {
        if (admin.getPhone() == null || admin.getPhone().trim().isEmpty()) {
            throw new Exception("手机号不能为空");
        }
        if (admin.getPassword() == null || admin.getPassword().trim().isEmpty()) {
            throw new Exception("密码不能为空");
        }

        Administrator existingAdmin = adminMapper.findByPhone(admin.getPhone());
        if (existingAdmin == null) {
            throw new Exception("管理员不存在");
        }

        if (!existingAdmin.getPassword().equals(admin.getPassword())) {
            throw new Exception("密码错误");
        }

        return existingAdmin;
    }

    @Override
    public void updateAdmin(Integer adminId, Administrator updateInfo) throws Exception {
        Administrator admin = adminMapper.findById(adminId);
        if (admin == null) {
            throw new Exception("管理员不存在");
        }

        if (updateInfo.getPhone() != null && !updateInfo.getPhone().isEmpty()) {
            Administrator existingAdmin = adminMapper.findByPhone(updateInfo.getPhone());
            if (existingAdmin != null && !existingAdmin.getId().equals(adminId)) {
                throw new Exception("手机号已被使用");
            }
        }

        updateInfo.setId(adminId);
        adminMapper.updateAdmin(updateInfo);
    }

    @Override
    public Administrator getAdminById(Integer adminId) throws Exception {
        Administrator admin = adminMapper.findById(adminId);
        if (admin == null) {
            throw new Exception("管理员不存在");
        }
        return admin;
    }

    @Override
    public List<NormalUser> getAllUsers() throws Exception {
        return userMapper.findAllNormalUsers();
    }

    @Override
    public void blockUser(Integer userId) throws Exception {
        NormalUser user = userMapper.findById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }
        user.setIsBlocked(1);
        userMapper.update(user);
    }

    @Override
    public void unblockUser(Integer userId) throws Exception {
        NormalUser user = userMapper.findById(userId);
        if (user == null) {
            throw new Exception("用户不存在");
        }
        user.setIsBlocked(0);
        userMapper.update(user);
    }
}
