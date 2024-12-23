package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.ExerciseItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ExerciseItemMapper {
    List<ExerciseItem> getAllExerciseItems();
    ExerciseItem getExerciseItemById(@Param("id")int id);
    void insertExerciseItem(ExerciseItem exerciseItem);
}
