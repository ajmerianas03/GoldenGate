package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.Follow;
import com.GoldenGate.GoldenGate.user.User;

import java.util.List;

public interface FollowService {
    Follow followUser(User follower, User following);
    void unfollowUser(User follower, User following);
    List<Follow> getFollowers(User user);
    List<Follow> getFollowing(User user);
    Follow getFollowById(int followId);
}
