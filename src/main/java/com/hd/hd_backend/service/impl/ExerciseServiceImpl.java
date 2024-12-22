package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.ExerciseItem;
import com.hd.hd_backend.entity.ExerciseRecord;
import com.hd.hd_backend.mapper.ExerciseItemMapper;
import com.hd.hd_backend.mapper.ExerciseRecordMapper;
import com.hd.hd_backend.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ExerciseServiceImpl implements ExerciseService {
    @Autowired
    private ExerciseItemMapper exerciseItemMapper;
    @Autowired
    private ExerciseRecordMapper exerciseRecordMapper;
    public List<ExerciseItem> getAllExerciseItem(){
        return exerciseItemMapper.getAllExerciseItems();
    }

    @Override
    public void addExerciseItem(ExerciseItem exerciseItem) {

        exerciseItemMapper.insertExerciseItem(exerciseItem);

    }

    @Override
    public void  addExerciseRecord(ExerciseRecord exerciseRecord)
    {
        ExerciseItem exe=exerciseItemMapper.getExerciseItemById(exerciseRecord.getExerciseId());
        LocalTime time = LocalTime.parse(exerciseRecord.getDuration()); // 转换为 LocalTime
        double hours = time.getHour() + time.getMinute() / 60.0 + time.getSecond() / 3600.0; // 计算小时数
        exerciseRecord.setBurnedCaloris((int) (hours*exe.getCaloriesPerHour()));
        exerciseRecordMapper.addExerciseRecord(exerciseRecord);
    }

    @Override
    public  List<ExerciseRecord>  getUserExerciseRecord(int userId)
    {
        return exerciseRecordMapper.getExerciseRecordsByUserId(userId);
    }
}
