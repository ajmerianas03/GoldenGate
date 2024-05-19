package com.GoldenGate.GoldenGate.system.repository;

import com.GoldenGate.GoldenGate.system.model.Follow;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {


    List<Follow> findByFollower(User user);

    List<Follow> findByFollowing(User user);

    Follow findByFollowerAndFollowing(User follower, User following);
}
