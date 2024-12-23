package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.FoodItem;
import com.hd.hd_backend.entity.FoodRecord;
import com.hd.hd_backend.mapper.FoodMapper;
import com.hd.hd_backend.mapper.FoodRecordMapper;
import com.hd.hd_backend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {
    
    @Autowired
    private FoodMapper foodMapper;
    
    @Autowired
    private FoodRecordMapper foodRecordMapper;

    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodMapper.findAll();
    }

    @Override
    public FoodItem getFoodItemByName(String name) {
        return foodMapper.findByName(name);
    }

    @Override
    public List<FoodRecord> getFoodRecordsByUserId(int userId) {
        return foodRecordMapper.findByUserId(userId);
    }

    @Override
    public FoodRecord getFoodRecordById(int foodRecordId) {
        return foodRecordMapper.findById(foodRecordId);
    }

    @Override
    public void addFoodRecord(FoodRecord foodRecord) throws Exception {
        try {
            foodRecordMapper.insert(foodRecord);
        } catch (Exception e) {
            throw new Exception("添加食物记录失败");
        }
    }

    @Override
    public void updateFoodRecord(FoodRecord foodRecord) throws Exception {
        try {
            int result = foodRecordMapper.update(foodRecord);
            if (result == 0) {
                throw new Exception("未找到要更新的食物记录");
            }
        } catch (Exception e) {
            throw new Exception("更新食物记录失败");
        }
    }

    @Override
    public void deleteFoodRecord(int foodRecordId) throws Exception {
        try {
            int result = foodRecordMapper.delete(foodRecordId);
            if (result == 0) {
                throw new Exception("未找到要删除的食物记录");
            }
        } catch (Exception e) {
            throw new Exception("删除食物记录失败");
        }
    }
} 