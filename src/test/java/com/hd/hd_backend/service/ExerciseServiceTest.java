package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.ExerciseRecordDTO;
import com.hd.hd_backend.entity.ExerciseItem;
import com.hd.hd_backend.entity.ExerciseRecord;
import com.hd.hd_backend.mapper.ExerciseItemMapper;
import com.hd.hd_backend.mapper.ExerciseRecordMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ExerciseServiceTest {

    @Autowired
    private ExerciseService exerciseService;

    @MockBean
    private ExerciseItemMapper exerciseItemMapper;

    @MockBean
    private ExerciseRecordMapper exerciseRecordMapper;

    private ExerciseItem exerciseItem;
    private ExerciseRecord exerciseRecord;

    @BeforeEach
    void setUp() {
        exerciseItem = new ExerciseItem();
        exerciseItem.setExerciseId(1);
        exerciseItem.setName("跑步");
        exerciseItem.setCaloriesPerHour(400);

        exerciseRecord = new ExerciseRecord();
        exerciseRecord.setExerciseRecordId(1);
        exerciseRecord.setExerciseId(1);
        exerciseRecord.setUserId(1);
        exerciseRecord.setDuration("00:30:00");
    }

    @Test
    void testGetAllExerciseItem() throws Exception {
        List<ExerciseItem> exerciseItems = Arrays.asList(exerciseItem);
        when(exerciseItemMapper.getAllExerciseItems()).thenReturn(exerciseItems);

        List<ExerciseItem> result = exerciseService.getAllExerciseItem();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("跑步", result.get(0).getName());
    }

    @Test
    void testAddExerciseItem() throws Exception {
        exerciseService.addExerciseItem(exerciseItem);
        verify(exerciseItemMapper).insertExerciseItem(any(ExerciseItem.class));
    }

    @Test
    void testAddExerciseRecord() throws Exception {
        ExerciseRecord record = new ExerciseRecord();
        record.setExerciseId(1);
        record.setUserId(1);
        record.setDate("2024-01-01 10:00:00");
        record.setDuration("00:30:00");
        
        when(exerciseItemMapper.getExerciseItemById(1)).thenReturn(exerciseItem);
        
        exerciseService.addExerciseRecord(record);
        
        verify(exerciseRecordMapper).addExerciseRecord(any(ExerciseRecord.class));
    }

    @Test
    void testGetUserExerciseRecord() throws Exception {
        ExerciseRecordDTO exerciseRecordDTO = new ExerciseRecordDTO();
        exerciseRecordDTO.setExerciseRecordId(1);
        exerciseRecordDTO.setUserId(1);
        exerciseRecordDTO.setExerciseId(1);
        exerciseRecordDTO.setDuration("00:30:00");
        exerciseRecordDTO.setDate("2024-01-01 10:00:00");
        exerciseRecordDTO.setExerciseName("跑步");
        exerciseRecordDTO.setBurnedCaloris(200);

        List<ExerciseRecordDTO> exerciseRecords = Arrays.asList(exerciseRecordDTO);
        when(exerciseRecordMapper.getExerciseRecordsByUserId(1)).thenReturn(exerciseRecords);

        List<ExerciseRecordDTO> result = exerciseService.getUserExerciseRecord(1);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getExerciseRecordId());
    }
} 