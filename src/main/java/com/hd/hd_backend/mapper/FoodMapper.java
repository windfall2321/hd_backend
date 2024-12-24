package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.FoodItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FoodMapper {
    List<FoodItem> findAll();
    FoodItem findByName(@Param("name") String name);
    void insert(FoodItem foodItem);
    int update(FoodItem foodItem);
    int delete(@Param("foodId") Integer foodId);
    FoodItem findById(@Param("foodId") Integer foodId);
}
