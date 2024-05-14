package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.user.User;

import java.util.List;

public interface PostService {
    Post savePost(User userDetails, Post post);

    Post updatePost(Long id, Post updatedPost);

    void deletePost(Long id);

    Post getPostById(Long id);

    List<Post> getAllPosts();
}
