package com.hd.hd_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "饮食分析")
public class DietAnalysis {

    @Schema(description = "分析ID")
    private int analysisId;

    @Schema(description = "用户ID")
    private int userId;

    @Schema(description = "总热量")
    private int calories;

    @Schema(description = "建议")
    private String suggestion;

    @Schema(description = "脂肪")
    private double fat;

    @Schema(description = "蛋白质")
    private double protein;

    @Schema(description = "碳水化合物")
    private double carbohydrates;

    @Schema(description = "膳食纤维")
    private double dietaryFiber;

    @Schema(description = "钠")
    private double sodium;

    @Schema(description = "钾")
    private double potassium;

    // Getters and Setters
    public int getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(int analysisId) {
        this.analysisId = analysisId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
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

    public double getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(double dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
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

    public void analyzeNutrients(List<FoodRecord> recordList) {
        int totalCalories = 0;
        double totalFat = 0;
        double totalProtein = 0;
        double totalCarbohydrates = 0;
        double totalDietaryFiber = 0;
        double totalSodium = 0;
        double totalPotassium = 0;

        for(FoodRecord foodRecord : recordList) {
            totalCalories += foodRecord.getCalories();
            totalFat += foodRecord.getFat();
            totalProtein += foodRecord.getProtein();
            totalCarbohydrates += foodRecord.getCarbohydrates();
            totalDietaryFiber += foodRecord.getDietaryFiber();
            totalSodium += foodRecord.getSodium();
            totalPotassium += foodRecord.getPotassium();
        }

        // Set the calculated values to the current object
        this.setCalories(totalCalories);
        this.setFat(totalFat);
        this.setProtein(totalProtein);
        this.setCarbohydrates(totalCarbohydrates);
        this.setDietaryFiber(totalDietaryFiber);
        this.setSodium(totalSodium);
        this.setPotassium(totalPotassium);
    }

    public void analyzeNutrients(NormalUser normalUser) {
        int weight=normalUser.getWeight();
        int height=normalUser.getHeight();
        int age=normalUser.getAge();
        int gender=normalUser.getGender();
        int activityFactor=normalUser.getActivityFactor();
        int baseCalories=0;
        if(gender==0)
        {
            baseCalories= (int) (66.5+(13.75*weight)+(5.003*height)-(6.75 *age));
        }
        else{
            baseCalories= (int) (66.5+(13.75*weight)+(5.003*height)-(6.75 *age));
        }
        int recommendCalories = 0;
        double recommendMinFat = 0;
        double recommendMaxFat = 0;

        double recommendProtein = 0;
        double recommendCarbohydrates = 0;
        double recommendDietaryFiber = 0;
        double recommendSugar = 0;
        double recommendSodium = 0;
        double recommendPotassium = 0;
    }

}
