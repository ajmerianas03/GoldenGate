package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.DTO.UserSkillDTO;
import com.GoldenGate.GoldenGate.system.model.UserSkill;
import com.GoldenGate.GoldenGate.user.User;

import java.util.List;

public interface UserSkillService {

    // CRUD operations for user skills

    UserSkill saveUserSkill(UserSkill userSkill);

    public UserSkill saveUserSkill(User UserDetails, UserSkill userSkill);


    //List<UserSkill> getAllUserSkills();

    //List<UserSkill> saveUserSkills(User userDetails, List<UserSkill> userSkills);

    void deleteUserSkill(Long id);


    UserSkillDTO updateUserSkill(Long id, UserSkillDTO updatedUserSkill);

    List<UserSkillDTO>  getUserSkillsByUserId(int userid);
}
