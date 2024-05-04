package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.config.JwtService;
import com.GoldenGate.GoldenGate.system.DTO.UserSkillDTO;
import com.GoldenGate.GoldenGate.system.model.UserSkill;
import com.GoldenGate.GoldenGate.system.repository.UserSkillRepository;
import com.GoldenGate.GoldenGate.system.service.UserSkillService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserSkillServiceImpl implements UserSkillService {

    @Autowired
    private UserSkillRepository userSkillRepository;

    private final UserDetailsService userDetailsService;

    private final JwtService jwtService;

    public UserSkillServiceImpl(UserSkillRepository userSkillRepository, UserDetailsService userDetailsService, JwtService jwtService) {
        this.userSkillRepository = userSkillRepository;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

//    @Override
//    public UserSkill saveUserSkill(UserSkill userSkill) {
//        return userSkillRepository.save(userSkill);
//    }

    @Override
    public UserSkill saveUserSkill(User UserDetails, UserSkill userSkill) {

        userSkill.setUser(UserDetails);
        return userSkillRepository.save(userSkill);
    }


    @Override
    public void deleteUserSkill(Long id) {
        userSkillRepository.deleteById(id);
    }



   // public List<UserSkill> getUserSkillsByUserId(int userId) {
    public List<UserSkillDTO> getUserSkillsByUserId(int userId) {
       // return userSkillRepository.findByUser_UserId(userId);
        List<UserSkill> userSkills = userSkillRepository.findByUser_UserId(userId);
        List<UserSkillDTO> userSkillDTOs = new ArrayList<>();

        for (UserSkill userSkill : userSkills) {
            UserSkillDTO userSkillDTO = new UserSkillDTO();
            userSkillDTO.setId(userSkill.getId());
            userSkillDTO.setSkillName(userSkill.getSkillName());
            userSkillDTOs.add(userSkillDTO);
        }

        return userSkillDTOs;
    }

    @Override
    public UserSkillDTO updateUserSkill(Long id, UserSkillDTO updatedUserSkill) {
        // Retrieve the UserSkill entity by its ID
        Optional<UserSkill> optionalUserSkill = userSkillRepository.findById(id);
        if (optionalUserSkill.isPresent()) {
            UserSkill userSkill = optionalUserSkill.get();

            // Update the UserSkill entity with data from the updatedUserSkill DTO
            userSkill.setSkillName(updatedUserSkill.getSkillName());

            // Save the updated UserSkill entity
            UserSkill updatedUserSkillEntity = userSkillRepository.save(userSkill);

            // Convert the updated UserSkill entity back to a UserSkillDTO object and return it
            UserSkillDTO updatedUserSkillDTO = new UserSkillDTO();
            updatedUserSkillDTO.setId(updatedUserSkillEntity.getId());
            updatedUserSkillDTO.setSkillName(updatedUserSkillEntity.getSkillName());
            return updatedUserSkillDTO;
        } else {
            // UserSkill with the given ID not found, return null or throw an exception
            return null;
        }
    }
    }




