package com.hd.hd_backend.dto;

import com.hd.hd_backend.entity.ExerciseRecord;

public class ExerciseRecordDTO extends ExerciseRecord {

    private String exerciseName;


    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }
}
