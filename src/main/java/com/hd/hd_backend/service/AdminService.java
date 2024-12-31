package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.Administrator;
import com.hd.hd_backend.entity.NormalUser;

public interface AdminService {
    Integer login(Administrator admin)throws Exception;
    void blockUser(NormalUser user)throws Exception;
}
