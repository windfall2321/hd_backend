package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.ExerciseItem;
import com.hd.hd_backend.entity.ExerciseRecord;

import java.util.List;

public interface ExerciseService {
    List<ExerciseItem> getAllExerciseItem();

    void addExerciseItem(ExerciseItem exerciseItem);

    void addExerciseRecord(ExerciseRecord exerciseRecord);

    List<ExerciseRecord> getUserExerciseRecord(int userId);



}
