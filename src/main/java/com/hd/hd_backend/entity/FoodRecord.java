package com.hd.hd_backend.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.utils.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "食物记录")
public class FoodRecord {

    @Schema(description = "食物记录ID")
    private int foodRecordId;

    @Schema(description = "记录时间")
    private String recordTime;

    @Schema(description = "用户ID")
    private int userId;

    @Schema(description = "食物ID")
    private int foodId;

    @Schema(description = "食物重量")
    private double foodWeight;

    @Schema(description = "热量")
    private int calories;

    @Schema(description = "脂肪")
    private double fat;

    @Schema(description = "蛋白质")
    private double protein;

    @Schema(description = "碳水化合物")
    private double carbohydrates;

    @Schema(description = "钠")
    private double sodium;

    @Schema(description = "钾")
    private double potassium;

    @Schema(description = "膳食纤维")
    private double dietaryFiber;

    // Getters and Setters
    public int getFoodRecordId() {
        return foodRecordId;
    }

    public void setFoodRecordId(int foodRecordId) {
        this.foodRecordId = foodRecordId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public double getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(double foodWeight) {
        this.foodWeight = foodWeight;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(double dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public String foodRecordDetails() {
        // 使用 ObjectMapper 进行对象转换
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 将 FoodRecord 对象转换为 JSON 字符串
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            // 错误处理
            return "{\"error_code\":500,\"error_message\":\"转换为JSON失败\"}";
        }
    }
}
