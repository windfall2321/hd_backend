package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.Comment;
import java.util.List;

public interface CommentService {
    void createComment(Comment comment);
    void deleteComment(Integer commentId);
    void updateComment(Comment comment);
    List<Comment> getPostComments(Integer postId);
    List<Comment> getUserComments(Integer userId);
} 