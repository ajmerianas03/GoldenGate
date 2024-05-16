package com.GoldenGate.GoldenGate.system.controller;


import com.GoldenGate.GoldenGate.repository.UserRepository;
import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.system.model.Experience;
import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.service.ExperienceService;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/experiences")
public class ExperienceController {

    private final JwtService JwtService;
    private final ExperienceService experienceService;

    private final UserRepository repository;



    @GetMapping("/{id}")
    public ResponseEntity<Experience> getExperienceById(@PathVariable Integer id) {
        Experience experience = experienceService.getExperienceById(id);
        if (experience != null) {
            return ResponseEntity.ok(experience);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
/*
    @GetMapping("")
    public ResponseEntity<List<Experience>> getAllExperiences() {
        List<Experience> experiences = experienceService.getAllExperiences();
        return ResponseEntity.ok(experiences);
    }


 */

    @GetMapping("")
    public ResponseEntity<List<Experience>> getAllExperiences( @NonNull HttpServletRequest request,@NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain) {


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

                }
                User userDetails = optionalUser.get();
                // System.out.println("this is user details loaded "+userDetails);
                // System.out.println(" User userDetails in profile creation"+userDetails);

                int Userid= Math.toIntExact(userDetails.getUserId());
                List<Experience> experiences = experienceService.getAllExperiences(Userid);
                if (experiences == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }


                return ResponseEntity.ok(experiences);
            }

        } catch (Exception e) {
            System.out.println("try catch "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return null;



    }

    @PostMapping("")
    public ResponseEntity<Experience> createExperience(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                                       @NonNull FilterChain filterChain, @RequestBody Experience newExperience) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        jwt = authHeader.substring(7);
        userEmail = JwtService.extractUsername(jwt);

        if (userEmail != null) {
            Optional<User> optionalUser = repository.findByEmail(userEmail);
            if (optionalUser.isPresent()) {
                User userDetails = optionalUser.get();
                Experience createdExperience = experienceService.saveExperience(userDetails, newExperience);
                return new ResponseEntity<>(createdExperience, HttpStatus.CREATED);
            }
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Experience> updateExperience(@PathVariable Integer id, @RequestBody Experience updatedExperience) {
        Experience experience = experienceService.updateExperience(id, updatedExperience);
        if (experience != null) {
            return ResponseEntity.ok(experience);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExperience(@PathVariable Integer id) {
        experienceService.deleteExperience(id);
        return ResponseEntity.noContent().build();
    }
}

