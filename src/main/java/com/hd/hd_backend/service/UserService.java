package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.entity.User;

public interface UserService {

    NormalUser register(NormalUser normalUser) throws Exception;
    User login(NormalUser normalUser) throws Exception;
    void updateUser(Integer userId, NormalUser updateInfo) throws Exception;
    NormalUser getUserById(Integer userId) throws Exception;
} 