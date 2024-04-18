package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.model.UserSkill;
import com.GoldenGate.GoldenGate.system.repository.UserSkillRepository;
import com.GoldenGate.GoldenGate.system.service.UserSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Override
    public UserSkill saveUserSkill(UserSkill userSkill) {
        return userSkillRepository.save(userSkill);
    }

    @Override
    public UserSkill getUserSkillById(Long id) {
        return userSkillRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUserSkill(Long id) {
        userSkillRepository.deleteById(id);
    }

    @Override
    public UserSkill updateUserSkillById(Long id, UserSkill updatedUserSkill) {
        UserSkill existingUserSkill = userSkillRepository.findById(id).orElse(null);
        if (existingUserSkill != null) {
            // Update existingUserSkill with properties from updatedUserSkill
            existingUserSkill.setSkillName(updatedUserSkill.getSkillName());
            // Add more properties to update as needed
            // Example: existingUserSkill.setDescription(updatedUserSkill.getDescription());
            // Save and return the updated user skill
            return userSkillRepository.save(existingUserSkill);
        }
        return null; // Handle error if user skill not found
    }
}
