package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.NormalUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    NormalUser findByName(@Param("name") String name);
    NormalUser findByPhone(@Param("phone") String phone);
    void insert(NormalUser user);
} 