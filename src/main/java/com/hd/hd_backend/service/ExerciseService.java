package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.ExerciseRecordDTO;
import com.hd.hd_backend.entity.ExerciseItem;
import com.hd.hd_backend.entity.ExerciseRecord;

import java.util.List;

public interface ExerciseService {
    List<ExerciseItem> getAllExerciseItem() throws Exception;

    void addExerciseItem(ExerciseItem exerciseItem) throws Exception;

    void addExerciseRecord(ExerciseRecord exerciseRecord)throws Exception;

    List<ExerciseRecordDTO> getUserExerciseRecord(int userId)throws Exception;



}
