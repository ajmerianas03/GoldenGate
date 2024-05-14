package com.GoldenGate.GoldenGate.system.controller;



import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.repository.UserRepository;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.service.PostService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final JwtService JwtService;
    private final PostService postService;

    private final UserRepository repository;

    @Autowired
    public PostController(JwtService jwtService, PostService postService, UserRepository repository) {
        this.JwtService = jwtService;
        this.postService = postService;
        this.repository = repository;
    }


    @PostMapping("")
    public ResponseEntity<Post> createPost(@NonNull HttpServletRequest request,
                                           @NonNull HttpServletResponse response,
                                           @NonNull FilterChain filterChain,
                                           @RequestBody Post newPost) throws ServletException, IOException {
        try {
            //System.out.println("in profile creation");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            //System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            //System.out.println(jwt+" this is jwt");
            userEmail = JwtService.extractUsername(jwt);
            // System.out.println(userEmail+" this is user email");
            if (userEmail != null ){

                // System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser "+optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }User userDetails = optionalUser.get();
                // System.out.println("this is user details loaded "+userDetails);
                // System.out.println(" User userDetails in profile creation"+userDetails);

                int Userid= Math.toIntExact(userDetails.getUserId());
                Post createdPost = postService.savePost(userDetails, newPost);
                return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }





    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        Post post = postService.updatePost(id, updatedPost);
        if (post != null) {
            return ResponseEntity.ok(post);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}

