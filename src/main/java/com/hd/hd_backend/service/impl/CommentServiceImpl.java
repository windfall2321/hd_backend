package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.dto.CommentDTO;
import com.hd.hd_backend.entity.Comment;
import com.hd.hd_backend.mapper.CommentMapper;
import com.hd.hd_backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void createComment(Comment comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public void deleteComment(Integer commentId) {
        commentMapper.deleteComment(commentId);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.updateComment(comment);
    }

    @Override
    public List<CommentDTO> getPostComments(Integer postId) {
        return commentMapper.getPostComments(postId);
    }

    @Override
    public List<CommentDTO> getUserComments(Integer userId) {
        return commentMapper.getUserComments(userId);
    }
} 