package com.GoldenGate.GoldenGate.system.repository;
import com.GoldenGate.GoldenGate.system.model.Experience;
import com.GoldenGate.GoldenGate.system.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Integer> {


   // Experience findByUserId(int userId);

    //Experience findByUser_UserId(int userId);
    Experience findByUser_UserId(Integer userId);
}

