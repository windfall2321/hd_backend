package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.dto.FoodRecordDTO;
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
    public List<FoodRecordDTO> getFoodRecordsByUserId(int userId) {
        return foodRecordMapper.findByUserId(userId);
    }

    @Override
    public FoodRecordDTO getFoodRecordById(int foodRecordId) {
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

    @Override
    public FoodItem getFoodItemById(Integer foodId) {
        return foodMapper.findById(foodId);
    }

    @Override
    public void addFoodItem(FoodItem foodItem) throws Exception {
        try {
            // 检查食物名是否已存在
            if (foodMapper.findByName(foodItem.getName()) != null) {
                throw new Exception("食物名称已存在");
            }
            foodMapper.insert(foodItem);
        } catch (Exception e) {
            throw new Exception("添加食物失败: " + e.getMessage());
        }
    }

    @Override
    public void updateFoodItem(FoodItem foodItem) throws Exception {
        try {
            // 检查食物是否存在
            FoodItem existingFood = foodMapper.findById(foodItem.getFoodid());
            if (existingFood == null) {
                throw new Exception("食物不存在");
            }
            
            // 检查新名称是否与其他食物重复
            FoodItem foodWithSameName = foodMapper.findByName(foodItem.getName());
            if (foodWithSameName != null && !foodWithSameName.getFoodid().equals(foodItem.getFoodid())) {
                throw new Exception("食物名称已存在");
            }
            
            int result = foodMapper.update(foodItem);
            if (result == 0) {
                throw new Exception("更新食物失败");
            }
        } catch (Exception e) {
            throw new Exception("更新食物失败: " + e.getMessage());
        }
    }

    @Override
    public void deleteFoodItem(Integer foodId) throws Exception {
        try {
            // 检查食物是否存在
            if (foodMapper.findById(foodId) == null) {
                throw new Exception("食物不存在");
            }
            
            int result = foodMapper.delete(foodId);
            if (result == 0) {
                throw new Exception("删除食物失败");
            }
        } catch (Exception e) {
            throw new Exception("删除食物失败: " + e.getMessage());
        }
    }

    @Override
    public FoodItem findByNameLike(String name) {
        return foodMapper.findByNameLike(name);
    }
} 