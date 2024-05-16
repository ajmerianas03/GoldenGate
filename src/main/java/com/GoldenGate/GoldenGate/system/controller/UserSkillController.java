package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.repository.UserRepository;
import com.GoldenGate.GoldenGate.system.DTO.UserSkillDTO;
import com.GoldenGate.GoldenGate.system.model.UserSkill;
import com.GoldenGate.GoldenGate.system.service.UserSkillService;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/userskills")
@RequiredArgsConstructor
public class UserSkillController {

    private final UserSkillService userSkillService;

    private final UserRepository repository;

    private final JwtService JwtService;



    @PostMapping("")
    public ResponseEntity<List<UserSkill>> saveUserSkills(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                                          @NonNull FilterChain filterChain, @RequestBody List<UserSkill> userSkills) throws ServletException, IOException {

        try {
            // Extract JWT token from the request header
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String userEmail;

            // Validate the JWT token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return null;
            }
            jwt = authHeader.substring(7);
            userEmail = JwtService.extractUsername(jwt);
            if (userEmail != null) {
                // Fetch the user details from the repository using the email
                Optional<User> optionalUser = repository.findByEmail(userEmail);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");
                }
                User userDetails = optionalUser.get();
                // Initialize a list to store the saved user skills
                List<UserSkill> savedUserSkills = new ArrayList<>();
                // Iterate over the list of user skills and save each one
                for (UserSkill userSkill : userSkills) {
                    savedUserSkills.add(userSkillService.saveUserSkill(userDetails, userSkill));
                }
                // Return the list of saved user skills in the response
                return new ResponseEntity<>(savedUserSkills, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            // Handle any exceptions and return an internal server error response
            System.out.println("try catch " + e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }





    @GetMapping("/me")
    public ResponseEntity<List<UserSkillDTO>> getUserSkillByJwtToken(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
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

                }User userDetails = optionalUser.get();
                Integer Userid = Math.toIntExact(((User) userDetails).getUserId());
                //int Userid= userDetails.getUserId();
                List<UserSkillDTO> userSkills = userSkillService.getUserSkillsByUserId(Userid);
                if (userSkills == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }


                return ResponseEntity.ok(userSkills);
            }

        } catch (Exception e) {
            System.out.println("try catch "+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }


        return null;



    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSkill(@PathVariable Long id) {
        try {
            userSkillService.deleteUserSkill(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            // Log the error or handle it accordingly
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSkillDTO> updateUserSkill(@PathVariable("id") String id, @RequestBody UserSkillDTO updatedUserSkill) {
        try {
            Long skillId = Long.parseLong(id);
            UserSkillDTO updatedSkill = userSkillService.updateUserSkill(skillId, updatedUserSkill);
            if (updatedSkill != null) {
                return ResponseEntity.ok(updatedSkill);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            // Handle invalid ID format
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }







}
