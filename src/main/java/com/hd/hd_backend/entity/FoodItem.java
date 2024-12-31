package com.hd.hd_backend.entity;

import com.hd.hd_backend.utils.JsonUtils;



public class FoodItem {
    // getters and setters
    private Integer foodid;
    
    private String name;

    private String type;
    
    private Integer calories;

    private Double fat;

    private Double protein;

    private Double carbohydrates;

    private Double dietary_fiber;

    private Double potassium;

    private Double sodium;

    public void setFoodid(Integer id) {
        this.foodid = id;
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
    public Double getDietaryFiber() {
        return dietary_fiber;
    }
    public void setDietaryFiber(Double dietary_fiber) {
        this.dietary_fiber = dietary_fiber;
    }
    public Double getPotassium() {
        return potassium;
    }
    public void setPotassium(Double potassium) {
        this.potassium = potassium;
    }
    public Double getSodium() {
        return sodium;
    }
    public void setSodium(Double sodium) {
        this.sodium = sodium;
    }
    public Integer getFoodid() {
        return foodid;
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
    public void setFat(Double Fat){
        this.fat=Fat;
    }

    public Double getFat() {
        return fat;
    }
    public void setProtein(Double protein) {
        this.protein=protein;
    }
    public Double getProtein() {
        return protein;
    }

    public String NutritionalDetails() {
        Object nutritionInfo = new Object() {
            public String food_id= String.valueOf(foodid);
            public String food_name = name;
            public String food_type = type;
            public String calories_value = String.valueOf(calories);
            public String fat_value = String.valueOf(fat);
            public String protein_value = String.valueOf(protein);
            public String carbohydrates_value = String.valueOf(carbohydrates);
            public String dietary_fiber_value = String.valueOf(dietary_fiber);
            public String potassium_value = String.valueOf(potassium);
            public String sodium_value = String.valueOf(sodium);
        };

        // 使用JsonUtils将对象转换为JSON字符串
        return JsonUtils.toJson(nutritionInfo);
    }


}