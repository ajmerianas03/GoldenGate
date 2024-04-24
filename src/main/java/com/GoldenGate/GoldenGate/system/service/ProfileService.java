package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.user.User;

public interface ProfileService {

    Profile getProfileByJwtToken(String jwtToken);
    public Profile createNewProfile(User UserDetails, Profile newProfile);

}
