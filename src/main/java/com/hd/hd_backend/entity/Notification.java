package com.hd.hd_backend.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {
    private Integer notificationId;
    private String data;
    private Integer sent;
    private Integer userId;
    private Integer type;
    private String createTime;

    public Notification() {
        LocalDateTime now = LocalDateTime.now();
        // 格式化为 MySQL 的 DATETIME 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = now.format(formatter);
        this.createTime = time;
        this.sent = 0;  // 默认未发送
    }

    // Getters and Setters
    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getSent() {
        return sent;
    }

    public void setSent(Integer sent) {
        this.sent = sent;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}

