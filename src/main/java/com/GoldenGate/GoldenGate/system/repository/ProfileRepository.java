package com.GoldenGate.GoldenGate.system.repository;


import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    //Optional<Profile> findByUser(User user);
    Profile findByUser_UserId(Integer userId);

    //Profile findByUserID(Integer userId);
}

