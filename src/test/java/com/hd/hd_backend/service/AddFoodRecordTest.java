package com.hd.hd_backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import com.hd.hd_backend.entity.*;
import com.hd.hd_backend.mapper.FoodMapper;
import com.hd.hd_backend.mapper.FoodRecordMapper;
import com.hd.hd_backend.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AddFoodRecordTest {

    @Mock
    private FoodMapper foodMapper;

    @Mock
    private FoodRecordMapper foodRecordMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private FoodServiceImpl foodService;


    private FoodRecord record;

    @BeforeEach
    void init() throws Exception {
        record = new FoodRecord();
        record.setUserId(1);
        record.setFoodId(1);
        record.setFoodWeight(100);
        record.setCalories(120);
        record.setFat(2.5);
        record.setProtein(5.2);
        record.setCarbohydrates(10.0);
        record.setSodium(0.0);
        record.setPotassium(1.2);
        record.setDietaryFiber(0.3);
        record.setRecordTime(LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ISO_DATE_TIME));

        lenient(). when(userService.getUserById(1)).thenReturn(new NormalUser());
        when(foodMapper.findById(1)).thenReturn(new FoodItem());
    }

    @Test
    void testAddFoodRecord_ValidInput() {
        assertDoesNotThrow(() -> foodService.addFoodRecord(record));
        verify(foodRecordMapper).insert(any());
    }

    @Test
    void testAddFoodRecord_UserNotFound() throws Exception {
        record.setUserId(99999); // 模拟非法用户 ID
        when(userService.getUserById(99999)).thenReturn(null);

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("用户不存在", e.getMessage());
    }

    @Test
    void testAddFoodRecord_FoodNotFound() {
        when(foodMapper.findById(1)).thenReturn(null);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("食物不存在", e.getMessage());
    }

    @Test
    void testAddFoodRecord_InvalidWeight_high() {
        record.setFoodWeight(100000);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("食物重量必须在1到99999之间", e.getMessage());
    }
    @Test
    void testAddFoodRecord_InvalidWeight_low() {
        record.setFoodWeight(0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("食物重量必须在1到99999之间", e.getMessage());
    }

    @Test
    void testAddFoodRecord_InvalidCalories() {
        record.setCalories(0);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("热量必须为正数", e.getMessage());
    }

    @Test
    void testAddFoodRecord_InvalidFat() {
        record.setFat(-1);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("脂肪含量必须为正数或为空", e.getMessage());
    }

    @Test
    void testAddFoodRecord_InvalidCarbohydrates() {
        record.setCarbohydrates(-5);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("碳水化合物含量必须为正数或为空", e.getMessage());
    }

    @Test
    void testAddFoodRecord_InvalidSodium() {
        record.setSodium(-1);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("钠、钾或膳食纤维含量不能为负数", e.getMessage());
    }
    @Test
    void testAddFoodRecord_InvalidProtein() {
        record.setProtein(-1);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("蛋白质含量必须为正数或为空", e.getMessage());
    }
    @Test
    void testAddFoodRecord_RecordTimeIsNull() {
        record.setRecordTime(null);

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("记录时间不能为空", e.getMessage());
    }

    @Test
    void testAddFoodRecord_RecordTimeIsEmpty() {
        record.setRecordTime("   "); // 空白字符串

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("记录时间不能为空", e.getMessage());
    }

    @Test
    void testAddFoodRecord_TimeInFuture() {
        record.setRecordTime(LocalDateTime.now().plusDays(1).format(DateTimeFormatter.ISO_DATE_TIME));
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("记录时间不能晚于当前时间", e.getMessage());
    }

    @Test
    void testAddFoodRecord_DBInsertFails_ShouldThrowWrappedException() {
        doThrow(new RuntimeException("DB failure")).when(foodRecordMapper).insert(any());

        Exception e = assertThrows(Exception.class, () -> {
            foodService.addFoodRecord(record);
        });

        assertTrue(e.getMessage().contains("添加食物记录失败"));
        verify(foodRecordMapper, times(1)).insert(any());
    }


    @Test
    void testAddFoodRecord_UserIdValidAndUserExists() throws Exception {
        record.setUserId(-1);
        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("用户不存在", e.getMessage());
    }
    @Test
    void testAddFoodRecord_NutrientsAreZero() {
        // 营养素校验：当脂肪、蛋白质、碳水化合物为0时，不应抛出异常
        // 对 if (field != 0 && field <= 0) 的补充测试
        record.setFat(0.0);
        record.setProtein(0.0);
        record.setCarbohydrates(0.0);
        assertDoesNotThrow(() -> foodService.addFoodRecord(record));
        verify(foodRecordMapper).insert(any(FoodRecord.class));
    }
    @Test
    void testAddFoodRecord_Branch_FoodIdIsZero() {
        // 测试 A || B 中的 A=true 的情况
        // 条件：foodId <= 0
        record.setFoodId(0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("食物不存在", e.getMessage());

        // 验证：由于 foodId <= 0，程序短路，不会调用 foodMapper.findById
        verify(foodMapper, never()).findById(anyInt());
    }

    @Test
    void testAddFoodRecord_Branch_FoodIdValidButFoodNotFound() {
        // 目标：测试 A || B 中的 A=false, B=true 的情况
        // 条件：foodId > 0, 但是 foodMapper.findById 返回 null
        record.setFoodId(999); // 一个有效的ID
        when(foodMapper.findById(999)).thenReturn(null); // 但是数据库找不到

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("食物不存在", e.getMessage());

        // 验证：foodMapper.findById 被调用了
        verify(foodMapper, times(1)).findById(999);
    }
    @Test
    void testAddFoodRecord_Branch_OnlySodiumIsNegative() {
        // 目标：测试 A || B || C 中的 A=true
        // 条件：sodium < 0
        record.setSodium(-1.0);
        // 确保其他条件为false
        record.setPotassium(1.0);
        record.setDietaryFiber(1.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("钠、钾或膳食纤维含量不能为负数", e.getMessage());
    }

    @Test
    void testAddFoodRecord_Branch_OnlyPotassiumIsNegative() {
        // 目标：测试 A || B || C 中的 A=false, B=true
        // 条件：sodium >= 0, potassium < 0
        record.setSodium(1.0);
        record.setPotassium(-1.0);
        // 确保其他条件为false
        record.setDietaryFiber(1.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("钠、钾或膳食纤维含量不能为负数", e.getMessage());
    }

    @Test
    void testAddFoodRecord_Branch_OnlyDietaryFiberIsNegative() {
        // 目标：测试 A || B || C 中的 A=false, B=false, C=true
        // 条件：sodium >= 0, potassium >= 0, dietaryFiber < 0
        record.setSodium(1.0);
        record.setPotassium(1.0);
        record.setDietaryFiber(-1.0);

        Exception e = assertThrows(IllegalArgumentException.class, () -> foodService.addFoodRecord(record));
        assertEquals("钠、钾或膳食纤维含量不能为负数", e.getMessage());
    }

}
