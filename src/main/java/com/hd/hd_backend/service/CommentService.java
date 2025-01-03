package com.hd.hd_backend.service;

import com.hd.hd_backend.dto.CommentDTO;
import com.hd.hd_backend.entity.Comment;
import java.util.List;

public interface CommentService {
    void createComment(Comment comment);
    void deleteComment(Integer commentId);
    void updateComment(Comment comment);
    List<CommentDTO> getPostComments(Integer postId);
    List<CommentDTO> getUserComments(Integer userId);
    List<CommentDTO> getAllComments();
} 