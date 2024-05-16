package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.Experience;
import com.GoldenGate.GoldenGate.user.User;

import java.util.List;

public interface ExperienceService {
    Experience saveExperience(User userDetails, Experience newExperience);

    Experience updateExperience(Integer id, Experience updatedExperience);

    void deleteExperience(Integer id);

    Experience getExperienceById(Integer id);

    List<Experience> getAllExperiences(int userid);
}
