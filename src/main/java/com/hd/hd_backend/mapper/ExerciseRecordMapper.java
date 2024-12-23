package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.ExerciseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ExerciseRecordMapper {
    void addExerciseRecord(ExerciseRecord record);
    void updateExerciseRecord(ExerciseRecord record);
    void deleteExerciseRecord(@Param("id") int id);
    ExerciseRecord getExerciseRecord(@Param("id")int id);
    List<ExerciseRecord> getExerciseRecords();
    List<ExerciseRecord> getExerciseRecordsByUserId(@Param("user_id") int user_id);
}
