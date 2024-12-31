package com.hd.hd_backend.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Post {
    private Integer postId;
    private String title;
    private Integer userId;
    private String content;
    private String tags;
    private String  timestamp;
    private Integer isOffending;
    public Post() {
        LocalDateTime now = LocalDateTime.now();
        // 格式化为 MySQL 的 DATETIME 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time  = now.format(formatter);
        this.timestamp = time;
    }
    // Getters and Setters
    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getIsOffending() {
        return isOffending;
    }

    public void setIsOffending(Integer isOffending) {
        this.isOffending = isOffending;
    }
} 