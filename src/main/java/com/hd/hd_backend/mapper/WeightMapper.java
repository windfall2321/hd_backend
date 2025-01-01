package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.Weight;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WeightMapper {
    void insertWeight(Weight weight);
    void deleteWeight(@Param("userId") Integer userId, @Param("time") String time);
    void updateWeight(Weight weight);
    List<Weight> getUserWeights(@Param("userId") Integer userId);
    Weight getLatestWeight(@Param("userId") Integer userId);
} 