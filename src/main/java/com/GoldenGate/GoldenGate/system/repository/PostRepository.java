package com.GoldenGate.GoldenGate.system.repository;

import com.GoldenGate.GoldenGate.system.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {




    List<Post> findByUser_UserId(Long userId);

    Page<Post> findByUser_UserId(Long userId, Pageable pageable);
}

