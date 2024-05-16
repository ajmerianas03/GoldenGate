package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.exceptions.InvalidTokenException;
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
        Integer userId = Math.toIntExact(((User) userDetails).getUserId());

        // Find the Profile by userId
        return profileRepository.findByUser_UserId(userId);
    }


    @Override
    public Profile createNewProfile(User UserDetails, Profile newProfile) {




        //System.out.println("Serviceimpl");
       /* String userEmail = jwtService.extractUsername(jwtToken);
        System.out.println("After user email");
        System.out.println("email"+userEmail);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        System.out.println("User dtails"+userDetails);
        if (!jwtService.isTokenValid(jwtToken, userDetails)) {
            throw new InvalidTokenException("Invalid JWT token");
        }


        User user = ((User) userDetails);
        System.out.println("user data"+user);


        Profile existingProfile = profileRepository.findByUser_UserId(user.getUserId());

        if (existingProfile != null) {
            throw new RuntimeException("Profile already exists for user: " + userEmail);
        }

        // Set the user to the new profile and save it
        newProfile.setUser(user);

        */
        int Userid = Math.toIntExact(UserDetails.getUserId());
       // System.out.println(" User id in serviceimpl of profile"+Userid);
        Profile existingProfile = profileRepository.findByUser_UserId(Userid);
        if (existingProfile != null) {
            throw new RuntimeException("Profile already exists for user: " );
        }
        newProfile.setUser(UserDetails);
        //System.out.println("new profile object"+newProfile);

        return profileRepository.save(newProfile);
    }


}
