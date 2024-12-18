package com.hd.hd_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "食物项")
public class FoodItem {
    @Schema(description = "食物ID")
    private String id;
    
    @Schema(description = "食物名称")
    private String name;
    
    // getters and setters
} 