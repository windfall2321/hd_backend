package com.hd.hd_backend.mapper;

import com.hd.hd_backend.dto.FoodRecordDTO;
import com.hd.hd_backend.entity.FoodRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface FoodRecordMapper {

    List<FoodRecordDTO> findByUserId(int userId);
    FoodRecordDTO findById(int foodRecordId);
    void insert(FoodRecord foodRecord);
    int update(FoodRecord foodRecord);
    int delete(int foodRecordId);
    List<FoodRecordDTO> getAllFoodRecord(Integer userId);
}
