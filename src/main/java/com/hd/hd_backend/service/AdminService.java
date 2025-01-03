package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.Administrator;
import com.hd.hd_backend.entity.NormalUser;
import java.util.List;

public interface AdminService {
    Administrator login(Administrator admin) throws Exception;
    void updateAdmin(Integer adminId, Administrator updateInfo) throws Exception;
    Administrator getAdminById(Integer adminId) throws Exception;
    List<NormalUser> getAllUsers() throws Exception;
    void blockUser(Integer userId) throws Exception;
    void unblockUser(Integer userId) throws Exception;
}
