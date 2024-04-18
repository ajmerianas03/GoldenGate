package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(profile);
    }

    @PostMapping("")
    public ResponseEntity<Profile> createProfile(@RequestHeader("Authorization") String jwtToken,
                                                 @RequestBody Profile newProfile) {
        Profile createdProfile = profileService.createNewProfile(jwtToken, newProfile);
        return new ResponseEntity<>(createdProfile, HttpStatus.CREATED);
    }


}
