package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // Endpoint to retrieve profile by JWT token

    @GetMapping("/me")
    public ResponseEntity<Profile> getProfileByJwtToken(@RequestHeader("Authorization") String jwtToken) {
        Profile profile = profileService.getProfileByJwtToken(jwtToken);

        if (profile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Convert avatar and backgroundImage from binary to Base64
        if (profile.getAvatar() != null) {
            profile.setAvatar(Base64.getEncoder().encodeToString(profile.getAvatar()).getBytes());
        }
        if (profile.getBackgroundImage() != null) {
            profile.setBackgroundImage(Base64.getEncoder().encodeToString(profile.getBackgroundImage()).getBytes());
        }

        return ResponseEntity.ok(profile);
    }

    @PostMapping("")
    public ResponseEntity<Profile> createProfile(@RequestHeader("Authorization") String jwtToken,
                                                 @RequestBody Profile newProfile) {
        try {
            // Decode Base64 avatar and backgroundImage before saving
            if (newProfile.getAvatar() != null) {
                newProfile.setAvatar(Base64.getDecoder().decode(newProfile.getAvatar()));
            }
            if (newProfile.getBackgroundImage() != null) {
                newProfile.setBackgroundImage(Base64.getDecoder().decode(newProfile.getBackgroundImage()));
            }

            // Create new profile
            Profile createdProfile = profileService.createNewProfile(jwtToken, newProfile);
            return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
