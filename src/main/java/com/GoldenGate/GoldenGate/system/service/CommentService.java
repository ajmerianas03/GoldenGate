package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.Comment;

import java.util.List;

public interface CommentService {
    // Method to save a comment
    Comment saveComment(Comment comment);

    // Method to update an existing comment
    Comment updateComment(Long id, Comment updatedComment);

    // Method to delete a comment by its ID
    void deleteComment(Long id);

    // Method to retrieve a comment by its ID
    Comment getCommentById(Long id);

    // Method to retrieve all comments for a specific post
    List<Comment> getAllCommentsForPost(Long postId);

    // Method to retrieve all comments in descending order of creation timestamp
//    List<Comment> getAllCommentsInDescendingOrder();

    // Method to retrieve all comments by a specific user
//    List<Comment> getAllCommentsByUser(Long userId);

}
