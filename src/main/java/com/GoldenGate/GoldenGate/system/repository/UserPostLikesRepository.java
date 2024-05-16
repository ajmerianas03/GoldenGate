package com.GoldenGate.GoldenGate.system.repository;

import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.model.UserPostLikes;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserPostLikesRepository extends JpaRepository<UserPostLikes, Integer> {


    UserPostLikes findByUserAndPost(User user, Post post);

    @Query("SELECT upl FROM UserPostLikes upl WHERE upl.post.id = ?1")
    UserPostLikes findByPostId(Long postId);
}
