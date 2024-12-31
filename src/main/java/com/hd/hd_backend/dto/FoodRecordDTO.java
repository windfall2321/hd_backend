package com.hd.hd_backend.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hd.hd_backend.entity.FoodRecord;

public class FoodRecordDTO extends FoodRecord {

    private String foodname;

    // getter 和 setter 方法

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }



    public String foodRecordDetails() {
        // 使用 ObjectMapper 进行对象转换
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 将 FoodRecord 对象转换为 JSON 字符串
            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            // 错误处理
            e.printStackTrace();
            return "{\"error_code\":500,\"error_message\":\"转换为JSON失败: " + e.getMessage() + "\"}";
        }
    }
}
