package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.Weight;
import com.hd.hd_backend.mapper.WeightMapper;
import com.hd.hd_backend.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeightServiceImpl implements WeightService {
    @Autowired
    private WeightMapper weightMapper;

    @Override
    public void addWeight(Weight weight) {
        weightMapper.insertWeight(weight);
    }

    @Override
    public void deleteWeight(Integer userId, String time) {
        weightMapper.deleteWeight(userId, time);
    }

    @Override
    public void updateWeight(Weight weight) {
        weightMapper.updateWeight(weight);
    }

    @Override
    public List<Weight> getUserWeights(Integer userId) {
        return weightMapper.getUserWeights(userId);
    }

    @Override
    public Weight getLatestWeight(Integer userId) {
        return weightMapper.getLatestWeight(userId);
    }
} 