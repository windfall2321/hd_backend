package com.hd.hd_backend.mapper;

import com.hd.hd_backend.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {
    void insertPost(Post post);
    void deletePost(@Param("postId") Integer postId);
    void updatePost(Post post);
    List<Post> findVisiblePosts();
    List<Post> findUserPosts(@Param("userId") Integer userId);
} 