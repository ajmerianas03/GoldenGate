package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.model.UserPostLikes;
import com.GoldenGate.GoldenGate.system.repository.UserPostLikesRepository;
import com.GoldenGate.GoldenGate.system.service.UserPostLikesService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPostLikesServiceImpl implements UserPostLikesService {

    private final UserPostLikesRepository userPostLikeRepository;

    @Autowired
    public UserPostLikesServiceImpl(UserPostLikesRepository userPostLikeRepository) {
        this.userPostLikeRepository = userPostLikeRepository;
    }




    @Override
    public UserPostLikes likePost(User user, Post post) {


        UserPostLikes userPostLike = new UserPostLikes();
        userPostLike.setUser(user);
        userPostLike.setPost(post);
        userPostLikeRepository.save(userPostLike);


        return userPostLike;
    }

    @Override
    public UserPostLikes unlikePost(User user, Post post) {
        UserPostLikes userPostLike = userPostLikeRepository.findByUserAndPost(user, post);
        if (userPostLike != null) {
            userPostLikeRepository.delete(userPostLike);
        }
        return userPostLike;
    }
}
