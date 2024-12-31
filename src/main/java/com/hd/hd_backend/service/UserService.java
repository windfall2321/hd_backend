package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.entity.User;

public interface UserService {

    NormalUser register(UserDTO userDTO) throws Exception;
    User login(UserDTO userDTO) throws Exception;
    void updateUser(Integer userId, NormalUser updateInfo) throws Exception;
    NormalUser getUserById(Integer userId) throws Exception;
} 