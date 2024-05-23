package com.GoldenGate.GoldenGate.system.repository;


import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    //Optional<Profile> findByUser(User user);
    Profile findByUser_UserId(Integer userId);

    Profile findByUser_UserIdAndProfileId(Integer userId, Integer profileId);

    @Query("SELECT p FROM Profile p WHERE p.fullName LIKE %:keyword%")
    Page<Profile> findByFullNameContaining(String keyword, Pageable pageable);

    //Profile findByUserID(Integer userId);
}

