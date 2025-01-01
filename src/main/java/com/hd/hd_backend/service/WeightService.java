package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.Weight;
import java.util.List;

public interface WeightService {
    void addWeight(Weight weight);
    void deleteWeight(Integer userId, String time);
    void updateWeight(Weight weight);
    List<Weight> getUserWeights(Integer userId);
    Weight getLatestWeight(Integer userId);
} 