package com.hd.hd_backend.dto;

import com.hd.hd_backend.entity.Post;

public class PostDTO extends Post {
    private String postUserName;  // 发帖人的名字

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }
} 