package com.hd.hd_backend.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {
    private int notificationId;
    private String data;
    private int sent;
    private int userId;
    private int type;
    private String createTime;

    public Notification() {}
    public Notification( String data, int userId) {
        this.data = data;
        this.userId = userId;
        LocalDateTime now = LocalDateTime.now();
// 格式化为 MySQL 的 DATETIME 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.createTime  = now.format(formatter);
    }
    public int getNotificationId() {
        return notificationId;

    }
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }
    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }
    public int getSent() {
        return sent;
    }
    public void setSent(int sent) {
        this.sent = sent;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {

        this.type = type;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}

