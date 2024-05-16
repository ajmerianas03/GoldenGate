package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.CommentDTO;
import com.GoldenGate.GoldenGate.system.DTO.PostDTO;
import com.GoldenGate.GoldenGate.system.model.Comment;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.model.PostImage;
import com.GoldenGate.GoldenGate.system.model.UserPostLikes;
import com.GoldenGate.GoldenGate.system.repository.CommentRepository;
import com.GoldenGate.GoldenGate.system.repository.PostImageRepository;
import com.GoldenGate.GoldenGate.system.repository.PostRepository;
import com.GoldenGate.GoldenGate.system.service.PostServiceWithImageHandling;
import com.GoldenGate.GoldenGate.system.service.UserPostLikesService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceWithImageHandlingImpl  implements PostServiceWithImageHandling {


    private final PostRepository postRepository;

    private final PostImageRepository postImageRepository;

    private final UserPostLikesService userPostLikesService;

    private final CommentRepository commentRepository;

    @Autowired
    public PostServiceWithImageHandlingImpl(PostRepository postRepository, PostImageRepository postImageRepository, UserPostLikesService userPostLikesService, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.userPostLikesService = userPostLikesService;
        this.commentRepository = commentRepository;
    }

    public String savePostWithImages(User userDetails, PostDTO postDTO) {

        System.out.println("in ser impl");

        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        post.setUpdatedAt(null);
        post.setUser(userDetails);
        post.setCommentsCount(0);
        post.setLikesCount(0);

        Post savedPost = postRepository.save(post);

        System.out.println("save post after  " + savedPost);


        if (postDTO.getImages() != null && !postDTO.getImages().isEmpty()) {
            for (String imageData : postDTO.getImages()) {
                PostImage postImage = new PostImage();
                postImage.setImage(imageData);
                postImage.setPost(savedPost);
                postImageRepository.save(postImage);
            }

            // Return a success message

        }
        if (savedPost == null){
            return "null";
        }
        return "Post  saved successfully.";
    }

    @Override
    public List<PostDTO> getPostsByUserIdWithImages(Long userId) {
        // Retrieve posts by user ID
        List<Post> posts = postRepository. findByUser_UserId(userId);

        // Map Post entities to PostDTOs with images
        return posts.stream()
                .map(this::convertToDTOWithImages)
                .collect(Collectors.toList());
    }

    @Override
    public void deletePost(Long postId) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if(existingPost != null){
            postRepository.deleteById(postId);
        }

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
    public Integer likecount(Long postId, PostDTO likecount, User userdeatils){
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existingPost.setLikesCount(likecount.getLikesCount()+1);

        Post savedPost = postRepository.save(existingPost);

        UserPostLikes  UserPostLikes= userPostLikesService.likePost(userdeatils,savedPost);

        return  savedPost.getLikesCount();
    }

    @Override
    public Integer unlikecount(Long postId, PostDTO likecount, User userdeatils){
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existingPost.setLikesCount(likecount.getLikesCount()-1);

        Post savedPost = postRepository.save(existingPost);

        UserPostLikes  UserPostLikes= userPostLikesService.unlikePost(userdeatils,savedPost);

        return  savedPost.getLikesCount();
    }

    @Override
    public Integer commentcount(Long postId){
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        existingPost.setCommentsCount(existingPost.getCommentsCount()+1);



        Post savedPost = postRepository.save(existingPost);


        return  existingPost.getCommentsCount();
    }

    @Override
    public Integer newComment(Long postId, CommentDTO commentDTO, User userDetails) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        Comment comment = new Comment();

        comment.setContent(comment.getContent());
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        comment.setUpdatedAt(null);
        comment.setParentCommentId(commentDTO.getParentCommentId());
        comment.setPost(existingPost);
        comment.setUser(userDetails);

        Comment saveCommment = commentRepository.save(comment);

       Integer commentCount = commentcount(postId);


        return commentCount;
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
        Comment result = unComment(userDetails ,existingPost);
        if(result != null){

            existingPost.setLikesCount(existingPost.getLikesCount()-1);

            Post savedPost = postRepository.save(existingPost);

            return savedPost.getCommentsCount();
        }
        return null;
    }

    private PostDTO convertToDTOWithImages(Post post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setPostId(post.getPostId());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt().toLocalDateTime());
        postDTO.setUpdatedAt(post.getUpdatedAt().toLocalDateTime());
        postDTO.setCommentsCount(post.getCommentsCount());
        postDTO.setLikesCount(post.getLikesCount());

        // Fetch associated images
        List<PostImage> postImages = postImageRepository.findByPost(post);
        List<String> imageUrls = postImages.stream()
                .map(PostImage::getImage)
                .collect(Collectors.toList());
        postDTO.setImages(imageUrls);

        return postDTO;
    }



}

