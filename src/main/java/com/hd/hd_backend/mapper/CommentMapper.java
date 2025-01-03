package com.hd.hd_backend.mapper;

import com.hd.hd_backend.dto.CommentDTO;
import com.hd.hd_backend.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(Comment comment);
    void deleteComment(@Param("commentId") Integer commentId);
    void updateComment(Comment comment);
    List<CommentDTO> getPostComments(@Param("postId") Integer postId);
    List<CommentDTO> getUserComments(@Param("userId") Integer userId);
    List<CommentDTO> getAllComments();
    Comment findById(@Param("commentId") Integer commentId);
} 