package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.Administrator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdministratorMapper {
    Administrator selectByPrimaryKey(@Param("id") Integer id);
    Administrator selectByName(@Param("name") String name);
}
