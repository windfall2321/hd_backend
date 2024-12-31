package com.hd.hd_backend.service;

import com.hd.hd_backend.entity.Post;
import java.util.List;

public interface PostService {
    void createPost(Post post);
    void deletePost(Integer postId);
    void updatePost(Post post);
    List<Post> findVisiblePosts();
    List<Post> findUserPosts(Integer userId);
} 