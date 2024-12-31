package com.hd.hd_backend.entity;

import java.util.Date;

public class Post {
    private Integer postId;
    private String title;
    private Integer userId;
    private String content;
    private String tags;
    private Date timestamp;
    private Integer isOffending;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getIsOffending() {
        return isOffending;
    }

    public void setIsOffending(Integer isOffending) {
        this.isOffending = isOffending;
    }
} 