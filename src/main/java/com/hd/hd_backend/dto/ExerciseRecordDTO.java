package com.hd.hd_backend.dto;

public class ExerciseRecordDTO {
    private int exerciseRecordId;
    private int exerciseId;
    private int userId;
    private String date;
    private String duration;
    private int burnedCaloris;
    private String exerciseName;

    public int getExerciseId() {
        return exerciseId;
    }
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }
    public int getExerciseRecordId() {
        return exerciseRecordId;
    }
    public void setExerciseRecordId(int exerciseRecordId) {
        this.exerciseRecordId = exerciseRecordId;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public int getBurnedCaloris() {
        return burnedCaloris;
    }
    public void setBurnedCaloris(int burnedCaloris) {
        this.burnedCaloris = burnedCaloris;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
}
