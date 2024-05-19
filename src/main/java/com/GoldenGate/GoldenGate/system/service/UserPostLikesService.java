package com.GoldenGate.GoldenGate.system.service;


import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.model.UserPostLikes;
import com.GoldenGate.GoldenGate.user.User;

public interface UserPostLikesService {

    UserPostLikes likePost(User user, Post post);

    UserPostLikes unlikePost(User user, Post post);

    boolean getLikePostByUser(User user, Post post);

    //void unlikePost(User user, Post post);
}
