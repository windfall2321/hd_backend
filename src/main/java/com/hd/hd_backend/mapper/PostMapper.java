package com.hd.hd_backend.mapper;

import com.hd.hd_backend.dto.PostDTO;
import com.hd.hd_backend.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(Post post);
    void deletePost(@Param("postId") Integer postId);
    void updatePost(Post post);
    List<PostDTO> findVisiblePosts();
    List<PostDTO> findUserPosts(@Param("userId") Integer userId);
} 