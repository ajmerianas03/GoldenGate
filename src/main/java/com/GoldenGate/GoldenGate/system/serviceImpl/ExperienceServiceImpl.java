package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.model.Experience;
import com.GoldenGate.GoldenGate.system.repository.ExperienceRepository;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.GoldenGate.GoldenGate.system.service.*;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    @Autowired
    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public Experience saveExperience(User userDetails, Experience experience) {
        int userId = userDetails.getUserId();
        // Check if an experience already exists for the user
        Experience existingExperience = experienceRepository.findByUser_UserId(userId);
        if (existingExperience != null) {
            throw new RuntimeException("Experience already exists for user with ID: " + userId);
        }
        // Set the user to the new experience and save it
        experience.setUser(userDetails);
        return experienceRepository.save(experience);
    }


    @Override
    public Experience updateExperience(Integer id, Experience updatedExperience) {
        Optional<Experience> optionalExperience = experienceRepository.findById(id);
        if (optionalExperience.isPresent()) {
            Experience experience = optionalExperience.get();

            experience.setTitle(updatedExperience.getTitle());
            experience.setEmploymentType(updatedExperience.getEmploymentType());

            return experienceRepository.save(experience);
        } else {
            return null;
        }
    }

    @Override
    public void deleteExperience(Integer id) {
        experienceRepository.deleteById(id);
    }

    @Override
    public Experience getExperienceById(Integer id) {
        Optional<Experience> optionalExperience = experienceRepository.findById(id);
        return optionalExperience.orElse(null);
    }

    @Override
    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }
}
