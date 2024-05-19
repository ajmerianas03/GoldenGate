package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.DTO.EducationDTO;
import com.GoldenGate.GoldenGate.user.User;

import java.util.List;

public interface EducationService {


    //EducationDTO saveComment(User userDetails, EducationDTO education);

    EducationDTO saveEducation(User userDetails, EducationDTO education);

    EducationDTO updateEducation(Long id, User userDetails , EducationDTO updateddegree);

    boolean deleteDegree(Long id, User userDetails);



    List< EducationDTO> getEducationByUserId(User userDetails);
}
