package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.NormalUser;
import com.hd.hd_backend.dto.FoodRecordDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    NormalUser findByName(@Param("name") String name);
    NormalUser findByPhone(@Param("phone") String phone);
    NormalUser findById(@Param("id") Integer id);
    void insert(NormalUser user);
    void blockById (@Param("id") int id);
    void update(NormalUser user);

} 