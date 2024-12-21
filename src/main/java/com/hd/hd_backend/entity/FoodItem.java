package com.hd.hd_backend.entity;

import com.hd.hd_backend.utils.JsonUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;



@Schema(description = "食物项")
public class FoodItem {
    // getters and setters
    @Schema(description = "食物ID")
    private Integer food_id;
    
    @Schema(description = "食物名称")
    private String name;

    @Schema(description = "食物类型")
    private String type;
    
    @Schema(description = "卡路里")
    private Integer calories;

    @Schema(description = "脂肪")
    private Double fat;

    @Schema(description = "蛋白质")
    private Double protein;

    @Schema(description = "碳水化合物")
    private Double carbohydrates;

    @Schema(description = "膳食纤维")
    private Double dietary_fiber;

    @Schema(description = "糖")
    private Double sugar;

    @Schema(description = "钾")
    private Double potassium;

    @Schema(description = "钠")
    private Double sodium;

    public void setFood_id(Integer food_id) {
        this.food_id = food_id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }
    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }
    public Double getDietary_fiber() {
        return dietary_fiber;
    }
    public void setDietary_fiber(Double dietary_fiber) {
        this.dietary_fiber = dietary_fiber;
    }
    public Double getPotassium() {
        return potassium;
    }
    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }
    public Double getSugar() {
        return sugar;
    }
    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }
    public Double getSodium() {
        return sodium;
    }
    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }
    public Integer getFood_id() {
        return food_id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public Integer getCalories() {
        return calories;
    }

    public String NutritionalDetails() {
        Object nutritionInfo = new Object() {
            public String food_name = name;
            public String food_type = type;
            public String calories_value = String.valueOf(calories);
            public String fat_value = String.valueOf(fat);
            public String protein_value = String.valueOf(protein);
            public String carbohydrates_value = String.valueOf(carbohydrates);
            public String dietary_fiber_value = String.valueOf(dietary_fiber);
            public String sugar_value = String.valueOf(sugar);
            public String potassium_value = String.valueOf(potassium);
            public String sodium_value = String.valueOf(sodium);
        };

        // 使用JsonUtils将对象转换为JSON字符串
        return JsonUtils.toJson(nutritionInfo);
    }

} 