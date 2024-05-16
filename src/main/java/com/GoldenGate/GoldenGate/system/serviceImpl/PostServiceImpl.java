package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.PostDTO;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.repository.PostRepository;
import com.GoldenGate.GoldenGate.system.service.PostService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

//    private final PostRepository postRepository;
//
//    @Autowired
//    public PostServiceImpl(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    @Override
//    public PostDTO savePost(User userDetails, Post post) {
//        post.setUser(userDetails);
//        Post savedPost = postRepository.save(post);
//        return convertToDTO(savedPost);
//    }
//
//    @Override
//    public PostDTO updatePost(Long id, Post updatedPost) {
//        Optional<Post> optionalPost = postRepository.findById(id);
//        if (optionalPost.isPresent()) {
//            Post post = optionalPost.get();
//            post.setContent(updatedPost.getContent());
//            // Update other fields as needed
//            Post updated = postRepository.save(post);
//            return convertToDTO(updated);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public void deletePost(Long id) {
//        postRepository.deleteById(id);
//    }
//
//    @Override
//    public PostDTO getPostById(Long id) {
//        Optional<Post> optionalPost = postRepository.findById(id);
//        return optionalPost.map(this::convertToDTO).orElse(null);
//    }
//
//    @Override
//    public List<PostDTO> getAllPosts() {
//        List<Post> posts = postRepository.findAll();
//        return posts.stream().map(this::convertToDTO).collect(Collectors.toList());
//    }
//
//    // Helper method to convert Post entity to PostDTO
//    private PostDTO convertToDTO(Post post) {
//        PostDTO postDTO = new PostDTO();
//        postDTO.setPostId(post.getPostId());
//        postDTO.setContent(post.getContent());
//        postDTO.setCreatedAt(post.getCreatedAt().toLocalDateTime());
//        postDTO.setUpdatedAt(post.getUpdatedAt().toLocalDateTime());
//        postDTO.setUserId(post.getUser().getUserId()); // Assuming User has userId field
//        postDTO.setCommentsCount(post.getCommentsCount());
//        postDTO.setLikesCount(post.getLikesCount());
//        return postDTO;
//    }
}
