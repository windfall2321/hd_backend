package com.hd.hd_backend.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;

@Schema(description = "饮食记录")
public class FoodRecord {
    @Schema(description = "记录ID")
    private String id;
    
    @Schema(description = "食物项")
    private FoodItem foodItem;
    
    @Schema(description = "记录时间")
    private Date recordTime;
    
    // getters and setters
} 