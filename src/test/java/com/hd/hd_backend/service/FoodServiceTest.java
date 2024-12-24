package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.FoodItem;
import com.hd.hd_backend.entity.FoodRecord;
import com.hd.hd_backend.mapper.FoodMapper;
import com.hd.hd_backend.mapper.FoodRecordMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class FoodServiceTest {

    @Autowired
    private FoodService foodService;

    @MockBean
    private FoodMapper foodMapper;

    @MockBean
    private FoodRecordMapper foodRecordMapper;

    private FoodItem foodItem;
    private FoodRecord foodRecord;

    @BeforeEach
    void setUp() {
        foodItem = new FoodItem();
        foodItem.setFoodid(1);
        foodItem.setName("苹果");
        foodItem.setCalories(52);
        foodItem.setType("水果");

        foodRecord = new FoodRecord();
        foodRecord.setFoodRecordId(1);
        foodRecord.setFoodId(1);
        foodRecord.setUserId(1);
        foodRecord.setCalories(52);
    }

    @Test
    void testGetAllFoodItems() throws Exception {
        List<FoodItem> foodItems = Arrays.asList(foodItem);
        when(foodMapper.findAll()).thenReturn(foodItems);

        List<FoodItem> result = foodService.getAllFoodItems();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("苹果", result.get(0).getName());
    }

    @Test
    void testGetFoodItemByName() throws Exception {
        when(foodMapper.findByName("苹果")).thenReturn(foodItem);

        FoodItem result = foodService.getFoodItemByName("苹果");

        assertNotNull(result);
        assertEquals("苹果", result.getName());
    }

    @Test
    void testAddFoodRecord() throws Exception {
        when(foodMapper.findById(1)).thenReturn(foodItem);
        
        foodService.addFoodRecord(foodRecord);
        
        verify(foodRecordMapper).insert(any(FoodRecord.class));
    }

    @Test
    void testGetFoodRecordsByUserId() throws Exception {
        List<FoodRecord> foodRecords = Arrays.asList(foodRecord);
        when(foodRecordMapper.findByUserId(1)).thenReturn(foodRecords);

        List<FoodRecord> result = foodService.getFoodRecordsByUserId(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getFoodRecordId());
    }

    // 添加异常测试用例
    @Test
    void testAddFoodRecordWithInvalidFood() {
        when(foodMapper.findById(1)).thenReturn(null);
        
        assertThrows(Exception.class, () -> {
            foodService.addFoodRecord(foodRecord);
        }, "应该抛出异常当食物不存在时");
    }

    @Test
    void testAddFoodRecordWithNullRecord() {
        assertThrows(Exception.class, () -> {
            foodService.addFoodRecord(null);
        }, "应该抛出异常当记录为空时");
    }
} 