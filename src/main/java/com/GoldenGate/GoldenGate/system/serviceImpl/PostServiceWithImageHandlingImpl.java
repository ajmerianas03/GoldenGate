package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.CommentDTO;
import com.GoldenGate.GoldenGate.system.DTO.PostDTO;
import com.GoldenGate.GoldenGate.system.model.*;
import com.GoldenGate.GoldenGate.system.repository.CommentRepository;
import com.GoldenGate.GoldenGate.system.repository.PostImageRepository;
import com.GoldenGate.GoldenGate.system.repository.PostRepository;
import com.GoldenGate.GoldenGate.system.repository.ProfileRepository;
import com.GoldenGate.GoldenGate.system.service.PostServiceWithImageHandling;
import com.GoldenGate.GoldenGate.system.service.UserPostLikesService;
import com.GoldenGate.GoldenGate.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceWithImageHandlingImpl implements PostServiceWithImageHandling {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserPostLikesService userPostLikesService;
    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceWithImageHandlingImpl(PostRepository postRepository, PostImageRepository postImageRepository, UserPostLikesService userPostLikesService, CommentRepository commentRepository, ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.userPostLikesService = userPostLikesService;
        this.commentRepository = commentRepository;
        this.profileRepository = profileRepository;
        this.modelMapper = new ModelMapper();
    }

    public String savePostWithImages(User userDetails, PostDTO postDTO) {
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        post.setUpdatedAt(null);
        post.setUser(userDetails);
        post.setCommentsCount(0);
        post.setLikesCount(0);

        Post savedPost = postRepository.save(post);

        if (postDTO.getImages() != null && !postDTO.getImages().isEmpty()) {
            for (String imageData : postDTO.getImages()) {
                PostImage postImage = new PostImage();
                postImage.setImage(imageData);
                postImage.setPost(savedPost);
                postImageRepository.save(postImage);
            }
        }
        return savedPost != null ? "Post saved successfully." : "Post saving failed.";
    }

    @Override
    public List<PostDTO> getPostsByUserIdWithImages(Long userId, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Post> postPage = postRepository.findByUser_UserId(userId, pageable);

            if (postPage.isEmpty()) {
                return Collections.emptyList();
            }

            return postPage.getContent().stream()
                    .map(this::convertToDTOWithImages)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving posts", e);
        }
    }

    @Override
    public void deletePost(Long postId) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        postRepository.deleteById(postId);
    }

    @Override
    public PostDTO updatePost(Long postId, Post updatedPost) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        existingPost.setContent(updatedPost.getContent());
        existingPost.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        existingPost.setCommentsCount(updatedPost.getCommentsCount());
        existingPost.setLikesCount(updatedPost.getLikesCount());

        Post savedPost = postRepository.save(existingPost);

        return convertToDTOWithImages(savedPost);
    }

    @Override
    public Integer likecount(Long postId, PostDTO likecount, User userDetails) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existingPost.setLikesCount(likecount.getLikesCount() + 1);

        Post savedPost = postRepository.save(existingPost);
        userPostLikesService.likePost(userDetails, savedPost);

        return savedPost.getLikesCount();
    }

    @Override
    public Integer unlikecount(Long postId, PostDTO likecount, User userDetails) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existingPost.setLikesCount(likecount.getLikesCount() - 1);

        Post savedPost = postRepository.save(existingPost);
        userPostLikesService.unlikePost(userDetails, savedPost);

        return savedPost.getLikesCount();
    }

    @Override
    public Integer commentcount(Long postId) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existingPost.setCommentsCount(existingPost.getCommentsCount() + 1);

        postRepository.save(existingPost);

        return existingPost.getCommentsCount();
    }

    @Override
    public Integer newComment(Long postId, CommentDTO commentDTO, User userDetails) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        comment.setUpdatedAt(null);
        comment.setParentCommentId(commentDTO.getParentCommentId());
        comment.setPost(existingPost);
        comment.setUser(userDetails);

        commentRepository.save(comment);

        return commentcount(postId);
    }

    @Override
    public Comment unComment(User user, Post post) {
        Comment removeComment = (Comment) commentRepository.findByUserAndPost(user, post);
        if (removeComment != null) {
            commentRepository.delete(removeComment);
        }
        return removeComment;
    }

    @Override
    public Integer removeComment(Long postId, CommentDTO commentDTO, User userDetails) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Comment result = unComment(userDetails, existingPost);
        if (result != null) {
            existingPost.setCommentsCount(existingPost.getCommentsCount() - 1);
            postRepository.save(existingPost);
            return existingPost.getCommentsCount();
        }
        return null;
    }

    @Override
    public Page<CommentDTO> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findByPostPostId(postId, pageable);
        List<CommentDTO> commentDTOs = commentPage.getContent().stream()
                .map(this::convertToCommentDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(commentDTOs, pageable, commentPage.getTotalElements());
    }

    private CommentDTO convertToCommentDTO(Comment comment) {
        User user = comment.getUser();
        Profile profile = profileRepository.findByUser_UserId(user.getUserId());

        String avatar = profile != null ? profile.getAvatar() : null;
        String fullName = profile != null ? profile.getFullName() : null;
        Integer profileId = profile != null ? profile.getProfileId() : null;

        return CommentDTO.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .parentCommentId(comment.getParentCommentId())
                .postId(comment.getPost().getPostId())
                .userId(user.getUserId())
                .avatar(avatar)
                .fullName(fullName)
                .profileId(profileId)
                .build();
    }

    private PostDTO convertToDTOWithImages(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(post.getPostId());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt().toLocalDateTime());

        // Handle potential null for updatedAt
        if (post.getUpdatedAt() != null) {
            postDTO.setUpdatedAt(post.getUpdatedAt().toLocalDateTime());
        } else {
            postDTO.setUpdatedAt(null);
        }

        postDTO.setCommentsCount(post.getCommentsCount());
        postDTO.setLikesCount(post.getLikesCount());

        User user = post.getUser();
        postDTO.setPost_Like_Status(userPostLikesService.getLikePostByUser(user, post));

        // Fetch associated images
        List<PostImage> postImages = postImageRepository.findByPost(post);
        List<String> imageUrls = postImages.stream()
                .map(PostImage::getImage)
                .collect(Collectors.toList());
        postDTO.setImages(imageUrls);

        return postDTO;
    }
}


