package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.FoodRecordDTO;
import com.hd.hd_backend.entity.FoodItem;
import com.hd.hd_backend.entity.FoodRecord;
import java.util.List;

public interface FoodService {
    // FoodItem相关方法
    List<FoodItem> getAllFoodItems();
    FoodItem getFoodItemByName(String name);
    FoodItem getFoodItemById(Integer foodId);
    void addFoodItem(FoodItem foodItem) throws Exception;
    void updateFoodItem(FoodItem foodItem) throws Exception;
    void deleteFoodItem(Integer foodId) throws Exception;
    
    // FoodRecord相关方法
    List<FoodRecordDTO> getFoodRecordsByUserId(int userId);
    FoodRecordDTO getFoodRecordById(int foodRecordId);
    void addFoodRecord(FoodRecord foodRecord) throws Exception;
    void updateFoodRecord(FoodRecord foodRecord) throws Exception;
    void deleteFoodRecord(int foodRecordId) throws Exception;
} 