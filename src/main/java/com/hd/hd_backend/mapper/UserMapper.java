package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByName(@Param("name") String name);
    User findByPhone(@Param("phone") String phone);
    NormalUser findById(@Param("id") Integer id);
    void insertNormalUser(NormalUser user);
    int insertUser(User user);
    void blockById(@Param("userId") Integer userId);
    void update(NormalUser user);
    List<NormalUser> findAllNormalUsers();
} 