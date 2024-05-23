package com.GoldenGate.GoldenGate.system.controller;


import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.repository.UserRepository;
import com.GoldenGate.GoldenGate.system.DTO.CommentDTO;
import com.GoldenGate.GoldenGate.system.DTO.PostDTO;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.repository.PostRepository;
import com.GoldenGate.GoldenGate.system.service.PostServiceWithImageHandling;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/postRequest")

public class PostRequestController {

    private final JwtService jwtService;
    private final UserRepository repository;
    private final PostServiceWithImageHandling postServiceWithImageHandling;

    private final PostRepository postRepository;

    @Autowired
    public PostRequestController(JwtService jwtService, UserRepository repository, PostServiceWithImageHandling postServiceWithImageHandling, PostRepository postRepository) {
        this.jwtService = jwtService;
        this.repository = repository;
        this.postServiceWithImageHandling = postServiceWithImageHandling;
        this.postRepository = postRepository;
    }

    @PostMapping("")
    public ResponseEntity<String> savePostWithImages(@NonNull HttpServletRequest request,
                                                     @NonNull HttpServletResponse response,
                                                     @NonNull FilterChain filterChain,
                                                     @RequestBody PostDTO newPost) throws ServletException, IOException {


        try {


            System.out.println("in post creation try");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            System.out.println(jwt + " this is jwt");
            userEmail = jwtService.extractUsername(jwt);
            System.out.println(userEmail + " this is user email");
            if (userEmail != null) {

                System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser " + optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }
                User userDetails = optionalUser.get();
                System.out.println("this is user details loaded " + userDetails);
                System.out.println(" User userDetails in profile creation" + userDetails);


                String message = postServiceWithImageHandling.savePostWithImages(userDetails, newPost);

                return ResponseEntity.status(HttpStatus.OK).body(message);
            }

        } catch (Exception e) {
            System.out.println("try catch " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }


        return null;


    }

//    @GetMapping("/me")
//    public ResponseEntity<List<PostDTO>> getPostsByUserId(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
//                                                          @NonNull FilterChain filterChain) {
//
//
//        try {
//            //System.out.println("in profile creation");
//            final String authHeader = request.getHeader("Authorization");
//            final String jwt;
//            final String userEmail;
//
//            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//                filterChain.doFilter(request, response);
//                return null;
//            }
//            //System.out.println("after final variable assign");
//            jwt = authHeader.substring(7);
//            //System.out.println(jwt+" this is jwt");
//            userEmail = jwtService.extractUsername(jwt);
//            // System.out.println(userEmail+" this is user email");
//            if (userEmail != null) {
//
//                // System.out.println("in if user load by optional");
//                Optional<User> optionalUser = repository.findByEmail(userEmail);
//                System.out.println("optionalUser " + optionalUser);
//                if (!optionalUser.isPresent()) {
//                    throw new RuntimeException("User not found");
//
//                }
//                User userDetails = optionalUser.get();
//                // System.out.println("this is user details loaded "+userDetails);
//                // System.out.println(" User userDetails in profile creation"+userDetails);
//
//                int Userid = Math.toIntExact(userDetails.getUserId());
//                // Profile profile = profileRepository.findByUser_UserId(Userid);
//                List<PostDTO> postDTO = postServiceWithImageHandling.getPostsByUserIdWithImages((long) Userid);
//
//                if (postDTO == null) {
//                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//                }
//
//
//                return ResponseEntity.ok(postDTO);
//            }
//
//        } catch (Exception e) {
//            System.out.println("try catch " + e);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//
//
//        return null;
//
//
//    }

    @GetMapping("/me")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(
            HttpServletRequest request,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        try {
            final String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);

            if (userEmail == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            Optional<User> optionalUser = repository.findByEmail(userEmail);
            if (!optionalUser.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            User userDetails = optionalUser.get();
            long userId = userDetails.getUserId();
            List<PostDTO> postDTOs = postServiceWithImageHandling.getPostsByUserIdWithImages(userId, page, size);

            if (postDTOs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return ResponseEntity.ok(postDTOs);
        } catch (Exception e) {
            e.printStackTrace();  // Consider replacing with proper logging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Long postId,
                                              @RequestBody Post updatedPost,
                                              @NonNull HttpServletRequest request,
                                              @NonNull HttpServletResponse response,
                                              @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }

            jwt = authHeader.substring(7);
            userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null) {
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");
                }
                User userDetails = optionalUser.get();
                int userId = Math.toIntExact(userDetails.getUserId());

                // Verify that the post belongs to the authenticated user
                Optional<Post> optionalPost = postRepository.findById(postId);
                if (!optionalPost.isPresent() || !optionalPost.get().getUser().getUserId().equals((long) userId)) {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }

                PostDTO updatedPostDTO = postServiceWithImageHandling.updatePost(postId, updatedPost);
                return ResponseEntity.ok(updatedPostDTO);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserpost(@PathVariable Long id) {
        try {
            postServiceWithImageHandling.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Log the error or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("likeCount")
    public Integer likecount(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull FilterChain filterChain,
                             @RequestBody PostDTO postLike) throws ServletException, IOException {


        try {


            System.out.println("in post creation try");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            System.out.println(jwt + " this is jwt");
            userEmail = jwtService.extractUsername(jwt);
            System.out.println(userEmail + " this is user email");
            if (userEmail != null) {

                System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser " + optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }
                User userDetails = optionalUser.get();
                System.out.println("this is user details loaded " + userDetails);
                System.out.println(" User userDetails in profile creation" + userDetails);

                int Userid = Math.toIntExact(userDetails.getUserId());

                Long postId = postLike.getPostId();

                Integer likeCount=postServiceWithImageHandling.likecount(postId,postLike ,userDetails);

    // add missing logic
                return likeCount;

            }
        } catch (Exception e) {
            System.out.println("try catch " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request").getStatusCodeValue();
        }


        return null;


    }




    @DeleteMapping("unlikeCount")
    public Integer unlikecount(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull FilterChain filterChain,
                             @RequestBody PostDTO postLike) throws ServletException, IOException {


        try {


            System.out.println("in unlike creation try");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            System.out.println(jwt + " this is jwt");
            userEmail = jwtService.extractUsername(jwt);
            System.out.println(userEmail + " this is user email");
            if (userEmail != null) {

                System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser " + optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }
                User userDetails = optionalUser.get();
                System.out.println("this is user details loaded " + userDetails);
                System.out.println(" User userDetails in profile creation" + userDetails);

                int Userid = Math.toIntExact(userDetails.getUserId());

                Long postId = postLike.getPostId();

                Integer likeCount=postServiceWithImageHandling.unlikecount(postId,postLike ,userDetails);

                // add missing logic
                return likeCount;

            }
        } catch (Exception e) {
            System.out.println("try catch " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request").getStatusCodeValue();
        }


        return null;


    }



    @PostMapping("newComment")
    public Integer newComment(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull FilterChain filterChain,
                             @RequestBody CommentDTO commentDTO) throws ServletException, IOException {


        try {


            System.out.println("in post creation try");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            System.out.println(jwt + " this is jwt");
            userEmail = jwtService.extractUsername(jwt);
            System.out.println(userEmail + " this is user email");
            if (userEmail != null) {

                System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser " + optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }
                User userDetails = optionalUser.get();
                System.out.println("this is user details loaded " + userDetails);
                System.out.println(" User userDetails in profile creation" + userDetails);

                int Userid = Math.toIntExact(userDetails.getUserId());

                Long postId = commentDTO.getPostId();

                Integer commentCount=postServiceWithImageHandling.newComment(postId,commentDTO ,userDetails);

                // add missing logic
                return commentCount;

            }
        } catch (Exception e) {
            System.out.println("try catch " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request").getStatusCodeValue();
        }


        return null;


    }


    @DeleteMapping("removeComment")
    public Integer removeCommentcount(@NonNull HttpServletRequest request,
                               @NonNull HttpServletResponse response,
                               @NonNull FilterChain filterChain,
                               @RequestBody CommentDTO commentDTO) throws ServletException, IOException {


        try {


            System.out.println("in unlike creation try");
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            System.out.println("after final variable assign");
            jwt = authHeader.substring(7);
            System.out.println(jwt + " this is jwt");
            userEmail = jwtService.extractUsername(jwt);
            System.out.println(userEmail + " this is user email");
            if (userEmail != null) {

                System.out.println("in if user load by optional");
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                System.out.println("optionalUser " + optionalUser);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");

                }
                User userDetails = optionalUser.get();
                System.out.println("this is user details loaded " + userDetails);
                System.out.println(" User userDetails in profile creation" + userDetails);

                int Userid = Math.toIntExact(userDetails.getUserId());

                Long postId = commentDTO.getPostId();

                Integer likeCount=postServiceWithImageHandling. removeComment(postId,commentDTO ,userDetails);

                // add missing logic
                return likeCount;

            }
        } catch (Exception e) {
            System.out.println("try catch " + e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request").getStatusCodeValue();
        }


        return null;


    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<CommentDTO>> getCommentsByPostId(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<CommentDTO> commentsPage = postServiceWithImageHandling.getCommentsByPostId(postId, page, size);
            return ResponseEntity.ok(commentsPage);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}


