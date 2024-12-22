package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.FoodRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface FoodRecordMapper {

    List<FoodRecord> findByUserId(int userId);
    FoodRecord findById(int foodRecordId);
    void insert(FoodRecord foodRecord);
    int update(FoodRecord foodRecord);
    int delete(int foodRecordId);
}
