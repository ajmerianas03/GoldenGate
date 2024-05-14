package com.GoldenGate.GoldenGate.system.serviceImpl;


import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.repository.PostRepository;
import com.GoldenGate.GoldenGate.system.service.PostService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    @Override
    public Post savePost(User userDetails, Post post) {
        // Check if the user already has a post


        // Set the user ID in the post and save it
        post.setUser(userDetails);;
        return postRepository.save(post);
    }


    @Override
    public Post updatePost(Long id, Post updatedPost) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setContent(updatedPost.getContent());
            // Update other fields as needed
            return postRepository.save(post);
        } else {
            // Handle case when post with given id is not found
            return null;
        }
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post getPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }
}
