package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.Administrator;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.mapper.AdministratorMapper;
import com.hd.hd_backend.mapper.UserMapper;
import com.hd.hd_backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdministratorMapper administratorMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer login(Administrator admin)throws Exception {

        Administrator get_admin= administratorMapper.selectByName(admin.getName());
        if(get_admin==null){
            throw new Exception("用户名不存在");

        }
        if(admin.getPassword().equals(get_admin.getPassword())){
            return get_admin.getId();
        }
        else {
            throw new Exception("密码错误");
        }
    }

    @Override
    public void blockUser(NormalUser user) throws Exception {
        userMapper.blockById(user.getId());




    }
}
