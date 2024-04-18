package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.Profile;

public interface ProfileService {

    Profile getProfileByJwtToken(String jwtToken);
    public Profile createNewProfile(String jwtToken, Profile newProfile);

}
