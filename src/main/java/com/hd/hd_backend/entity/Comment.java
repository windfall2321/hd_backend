package com.hd.hd_backend.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Comment {
    private Integer commentId;
    private Integer userId;
    private Integer postId;
    private String content;
    private String timestamp;
    private Integer isOffending;

    public Comment() {
        LocalDateTime now = LocalDateTime.now();
        // 格式化为 MySQL 的 DATETIME 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = now.format(formatter);
        this.timestamp = time;
        this.isOffending = 0;
    }

    // Getters and Setters
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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