package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.repository.UserRepository;
import com.GoldenGate.GoldenGate.system.DTO.EducationDTO;
import com.GoldenGate.GoldenGate.system.service.EducationService;
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
@RequestMapping("/api/v1/education")
public class EducationController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final EducationService educationService;

    @Autowired
    public EducationController(JwtService jwtService, UserRepository userRepository, EducationService educationService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.educationService = educationService;
    }

    @PostMapping("")
    public ResponseEntity<EducationDTO> saveEducation(@NonNull HttpServletRequest request,
                                                      @NonNull HttpServletResponse response,
                                                      @NonNull FilterChain filterChain,
                                                      @RequestBody EducationDTO newEducation) throws ServletException, IOException {
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
                Optional<User> optionalUser = userRepository.findByEmail(userEmail);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");
                }
                User userDetails = optionalUser.get();
                EducationDTO savedEducation = educationService.saveEducation(userDetails, newEducation);
                return ResponseEntity.status(HttpStatus.OK).body(savedEducation);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return null;
    }

    @GetMapping("/me")
    public ResponseEntity<List<EducationDTO>> getEducationsByUser(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                                                  @NonNull FilterChain filterChain) {
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
                Optional<User> optionalUser = userRepository.findByEmail(userEmail);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");
                }
                User userDetails = optionalUser.get();
                List<EducationDTO> educationDTOList = educationService.getEducationByUserId(userDetails);
                return ResponseEntity.ok(educationDTOList);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    @PutMapping("/{educationId}")
    public ResponseEntity<EducationDTO> updateEducation(@PathVariable Long educationId,
                                                        @RequestBody EducationDTO updatedEducation,
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
                Optional<User> optionalUser = userRepository.findByEmail(userEmail);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");
                }
                User userDetails = optionalUser.get();
                EducationDTO updatedEducationDTO = educationService.updateEducation(educationId, userDetails, updatedEducation);
                return ResponseEntity.ok(updatedEducationDTO);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(@PathVariable Long id,
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
                Optional<User> optionalUser = userRepository.findByEmail(userEmail);
                if (!optionalUser.isPresent()) {
                    throw new RuntimeException("User not found");
                }
                User userDetails = optionalUser.get();
                boolean deleted = educationService.deleteDegree(id, userDetails);
                if (deleted) {
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return null;
    }
}
