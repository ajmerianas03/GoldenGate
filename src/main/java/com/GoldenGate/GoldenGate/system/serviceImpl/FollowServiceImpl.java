package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.model.Follow;
import com.GoldenGate.GoldenGate.system.repository.FollowRepository;
import com.GoldenGate.GoldenGate.system.service.FollowService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    @Override
    public Follow followUser(User follower, User following) {
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        return followRepository.save(follow);
    }

    @Override
    public void unfollowUser(User follower, User following) {
        Follow follow = followRepository.findByFollowerAndFollowing(follower, following);
        if (follow != null) {
            followRepository.delete(follow);
        }
    }

    @Override
    public List<Follow> getFollowers(User user) {
        return followRepository.findByFollowing(user);
    }

    @Override
    public List<Follow> getFollowing(User user) {
        return followRepository.findByFollower(user);
    }

    @Override
    public Follow getFollowById(int followId) {
        return followRepository.findById(followId).orElse(null);
    }
}
