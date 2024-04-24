package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.repository.UserRepository;
import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.repository.ProfileRepository;
import com.GoldenGate.GoldenGate.system.service.ProfileService;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final JwtService JwtService;

    private final ProfileService profileService;
   // private final UserDetailsService userDetailsService;

    private final UserRepository repository;

    @Autowired
    public ProfileController(com.GoldenGate.GoldenGate.config.JwtService jwtService, ProfileService profileService, UserDetailsService userDetailsService, UserRepository repository) {
        this.JwtService = jwtService;
        this.profileService = profileService;
        //this.userDetailsService = userDetailsService;
        this.repository = repository;
    }

    // Endpoint to retrieve profile by JWT token

    @GetMapping("/me")
    public ResponseEntity<Profile> getProfileByJwtToken(@RequestHeader("Authorization") String jwtToken) {
        Profile profile = profileService.getProfileByJwtToken(jwtToken);

        if (profile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return ResponseEntity.ok(profile);
    }

    @PostMapping("")
    public ResponseEntity<Profile> createProfile(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
                                                 @NonNull FilterChain filterChain,
                                                 @RequestBody Profile newProfile) throws ServletException, IOException {

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

                int Userid= userDetails.getUserId();
                Profile createdProfile = profileService.createNewProfile(userDetails, newProfile);

                return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
            }

        } catch (Exception e) {
            System.out.println("try catch "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return null;
    }


}
