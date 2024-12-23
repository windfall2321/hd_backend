package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.FoodItem;
import com.hd.hd_backend.entity.FoodRecord;
import java.util.List;

public interface FoodService {
    // FoodItem相关方法
    List<FoodItem> getAllFoodItems();
    FoodItem getFoodItemByName(String name);
    
    // FoodRecord相关方法
    List<FoodRecord> getFoodRecordsByUserId(int userId);
    FoodRecord getFoodRecordById(int foodRecordId);
    void addFoodRecord(FoodRecord foodRecord) throws Exception;
    void updateFoodRecord(FoodRecord foodRecord) throws Exception;
    void deleteFoodRecord(int foodRecordId) throws Exception;
} 