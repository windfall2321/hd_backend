package com.hd.hd_backend.dto;

import com.hd.hd_backend.entity.Comment;

public class CommentDTO extends Comment {
    private String commentUserName;  // 评论者的名字
    private String commentUserProfilePicture;  // 评论者的头像

    public String getCommentUserName() {
        return commentUserName;
    }

    public void setCommentUserName(String commentUserName) {
        this.commentUserName = commentUserName;
    }

    public String getCommentUserProfilePicture() {
        return commentUserProfilePicture;
    }

    public void setCommentUserProfilePicture(String commentUserProfilePicture) {
        this.commentUserProfilePicture = commentUserProfilePicture;
    }
} 