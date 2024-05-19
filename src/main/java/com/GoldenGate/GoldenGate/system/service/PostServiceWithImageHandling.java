package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.DTO.CommentDTO;
import com.GoldenGate.GoldenGate.system.DTO.PostDTO;
import com.GoldenGate.GoldenGate.system.model.Comment;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.user.User;

import java.util.List;

public interface PostServiceWithImageHandling {
//   public   String savePostWithImages(User userDetails, Post post, List<String> imageDataList);

   public   String savePostWithImages(User userDetails , PostDTO postDTO);

    //usefull
     //List<PostDTO> getPostsByUserIdWithImages(Long userId);

    //usefull
// List<PostDTO> getPostsByUserIdWithoutImages(Long userId);

    List<PostDTO> getPostsByUserIdWithImages(Long userId, int page, int size);

    //usefull
   void deletePost(Long postId);
    //usefull
      PostDTO updatePost(Long postId, Post updatedPost);


    Integer likecount(Long postId, PostDTO likecount, User userdeatils);

    Integer unlikecount(Long postId, PostDTO likecount, User userdeatils);

   // Integer commentcount(Long postId, PostDTO commentcount);

    Integer commentcount(Long postId);

    Integer newComment(Long postId, CommentDTO commentDTO, User userDetails);

    Comment unComment(User user, Post post);

    Integer removeComment(Long postId, CommentDTO commentDTO, User userDetails);

//    List<PostDTO> getRecentPosts(int count);

//    List<PostDTO> getPopularPosts(int count);

//    void likePost(Long postId, User user);

//    void addCommentToPost(Long postId, User user, String comment);

//    List<PostDTO> getPostsByPage(int pageNumber, int pageSize);





}
