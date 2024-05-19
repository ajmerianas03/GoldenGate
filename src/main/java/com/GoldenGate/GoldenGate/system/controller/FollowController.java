package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.system.model.Follow;
import com.GoldenGate.GoldenGate.system.service.FollowService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follows")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/follow")
    public ResponseEntity<Follow> followUser(@RequestParam("followerId") Long followerId,
                                             @RequestParam("followingId") Long followingId) {
        User follower = new User(); // Implement user retrieval based on ID
        User following = new User(); // Implement user retrieval based on ID

        Follow follow = followService.followUser(follower, following);
        return ResponseEntity.status(HttpStatus.CREATED).body(follow);
    }

    @DeleteMapping("/unfollow")
    public ResponseEntity<Void> unfollowUser(@RequestParam("followerId") Long followerId,
                                             @RequestParam("followingId") Long followingId) {
        User follower = new User(); // Implement user retrieval based on ID
        User following = new User(); // Implement user retrieval based on ID

        followService.unfollowUser(follower, following);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/followers")
    public ResponseEntity<List<Follow>> getFollowers(@RequestParam("userId") Long userId) {
        User user = new User(); // Implement user retrieval based on ID
        List<Follow> followers = followService.getFollowers(user);
        return ResponseEntity.ok(followers);
    }

    @GetMapping("/following")
    public ResponseEntity<List<Follow>> getFollowing(@RequestParam("userId") Long userId) {
        User user = new User(); // Implement user retrieval based on ID
        List<Follow> following = followService.getFollowing(user);
        return ResponseEntity.ok(following);
    }
}
