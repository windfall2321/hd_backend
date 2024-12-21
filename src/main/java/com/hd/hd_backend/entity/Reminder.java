package com.hd.hd_backend.entity;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "提醒实体")
public class Reminder {
    @Schema(description = "提醒ID")
    private Integer reminderId;

    @Schema(description = "提醒类型")
    private Integer type;

    @Schema(description = "提醒时间")
    private String time;

    @Schema(description = "用户ID")
    private Integer userId;

    @Schema(description = "是否开启(0:关闭 1:开启)")
    private Integer isOn;

    // Getters and Setters
    public Integer getReminderId() {
        return reminderId;
    }

    public void setReminderId(Integer reminderId) {
        this.reminderId = reminderId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsOn() {
        return isOn;
    }

    public void setIsOn(Integer isOn) {
        this.isOn = isOn;
    }
}
