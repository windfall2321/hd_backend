package com.hd.hd_backend.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Weight {
    private Integer userId;
    private Double weight;
    private String time;

    public Weight() {
        LocalDateTime now = LocalDateTime.now();
        // 格式化为 MySQL 的 DATETIME 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = now.format(formatter);
        this.time = time;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
} 