package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.UserDTO;
import com.hd.hd_backend.entity.NormalUser;

public interface UserService {

    NormalUser register(UserDTO userDTO) throws Exception;
    NormalUser login(UserDTO userDTO) throws Exception;
    void updateUser(Integer userId, NormalUser updateInfo) throws Exception;
    NormalUser getUserById(Integer userId) throws Exception;
} 