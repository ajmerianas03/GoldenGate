package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.exceptions.InvalidTokenException;
import com.GoldenGate.GoldenGate.system.exceptions.ProfileNotFoundException;
import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.repository.ProfileRepository;
import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.system.service.ProfileService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            ProfileRepository profileRepository) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile getProfileByJwtToken(String jwtToken) {
        // Validate the JWT token
        String userEmail = jwtService.extractUsername(jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (!jwtService.isTokenValid(jwtToken,userDetails)) {
            throw new InvalidTokenException("Invalid JWT token");
        }

        // Extract the username (email) from the JWT token
       // String userEmail = jwtService.extractUsername(jwtToken);

        // Load the UserDetails (User) using the email
        //UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (userDetails == null) {
            throw new RuntimeException("User details not found for email: " + userEmail);
        }

        // Get the userId from the loaded User
        Integer userId = ((User) userDetails).getUserId();

        // Find the Profile by userId
        return profileRepository.findByUser_UserId(userId);
    }


    @Override
    public Profile createNewProfile(String jwtToken, Profile newProfile) {

        String userEmail = jwtService.extractUsername(jwtToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (!jwtService.isTokenValid(jwtToken, userDetails)) {
            throw new InvalidTokenException("Invalid JWT token");
        }


        User user = ((User) userDetails);
        Profile existingProfile = profileRepository.findByUser_UserId(user.getUserId());
        if (existingProfile != null) {
            throw new RuntimeException("Profile already exists for user: " + userEmail);
        }

        // Set the user to the new profile and save it
        newProfile.setUser(user);
        return profileRepository.save(newProfile);
    }


}
