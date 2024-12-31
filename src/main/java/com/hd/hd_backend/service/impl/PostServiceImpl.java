package com.hd.hd_backend.service.impl;

import com.hd.hd_backend.entity.Post;
import com.hd.hd_backend.mapper.PostMapper;
import com.hd.hd_backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostMapper postMapper;

    @Override
    public void createPost(Post post) {
        postMapper.insertPost(post);
    }

    @Override
    public void deletePost(Integer postId) {
        postMapper.deletePost(postId);
    }

    @Override
    public void updatePost(Post post) {
        postMapper.updatePost(post);
    }

    @Override
    public List<Post> findVisiblePosts() {
        return postMapper.findVisiblePosts();
    }

    @Override
    public List<Post> findUserPosts(Integer userId) {
        return postMapper.findUserPosts(userId);
    }
} 