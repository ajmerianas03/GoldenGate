package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.UserSkill;

import java.util.List;

public interface UserSkillService {

    // CRUD operations for user skills
    UserSkill saveUserSkill(UserSkill userSkill);

    UserSkill getUserSkillById(Long id);

    //List<UserSkill> getAllUserSkills();

    void deleteUserSkill(Long id);

    UserSkill updateUserSkillById(Long id, UserSkill updatedUserSkill);


}
