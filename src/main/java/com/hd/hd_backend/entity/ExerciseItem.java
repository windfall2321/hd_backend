package com.hd.hd_backend.entity;

public class ExerciseItem {
    private int exerciseId;
    private String name;
    private int caloriesPerHour;


    public int getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCaloriesPerHour() {
        return caloriesPerHour;
    }
    public void setCaloriesPerHour(int caloriesPerHour) {
        this.caloriesPerHour = caloriesPerHour;
    }
}
