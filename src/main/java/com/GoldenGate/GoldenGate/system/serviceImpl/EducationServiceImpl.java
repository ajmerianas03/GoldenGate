package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.EducationDTO;
import com.GoldenGate.GoldenGate.system.model.Education;
import com.GoldenGate.GoldenGate.system.repository.EducationRepository;
import com.GoldenGate.GoldenGate.system.service.EducationService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;

    @Autowired
    public EducationServiceImpl(EducationRepository educationRepository) {
        this.educationRepository = educationRepository;
    }

    @Override
    public EducationDTO saveEducation(User userDetails, EducationDTO education) {
        Education education1 = new Education();

        education1.setDegreeId(education.getDegreeId());
        education1.setInstitution(education.getInstitution());
        education1.setGraduationYear(education.getGraduationYear());
        education1.setStartingYear(education.getStartingYear());
        education1.setUser(userDetails);

        Education savedEdu = educationRepository.save(education1);

        if (savedEdu != null) {
            return convertToDTO(savedEdu);
        }
        return null;
    }

    @Override
    public EducationDTO updateEducation(Long id, User userDetails, EducationDTO education) {
        Education education1 = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (education1.getUser().equals(userDetails)){

            if (education1 != null) {
                education1.setDegreeId(education.getDegreeId());
                education1.setInstitution(education.getInstitution());
                education1.setGraduationYear(education.getGraduationYear());
                education1.setStartingYear(education.getStartingYear());
                education1.setUser(userDetails);

                Education savedEdu = educationRepository.save(education1);

                if (savedEdu != null) {
                    return convertToDTO(savedEdu);
                }
            }
        }
        return null;
    }

    @Override
    public boolean deleteDegree(Long id, User userDetails) {
        Education education1 = educationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (education1.getUser().equals(userDetails)) {
            educationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public List<EducationDTO> getEducationByUserId(User userDetails) {
        List<Education> educationEntities = (List<Education>) educationRepository.findByUser_UserId(userDetails.getUserId());
        return convertToDTOList(educationEntities);
    }

    private EducationDTO convertToDTO(Education education) {
        EducationDTO dto = new EducationDTO();
        dto.setEducationId(education.getEducationId());
        dto.setDegreeId(education.getDegreeId());
        dto.setInstitution(education.getInstitution());
        dto.setStartingYear(education.getStartingYear());
        dto.setGraduationYear(education.getGraduationYear());
        return dto;
    }

    private List<EducationDTO> convertToDTOList(List<Education> educationEntities) {
        return educationEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
}
