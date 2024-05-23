package com.GoldenGate.GoldenGate.system.repository;

import com.GoldenGate.GoldenGate.system.model.Comment;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Object findByUserAndPost(User user, Post post);
    // You can define custom query methods here if needed

    Page<Comment> findByPostPostId(Long postId, Pageable pageable);
}
