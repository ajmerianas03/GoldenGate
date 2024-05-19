package com.GoldenGate.GoldenGate.system.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EducationDTO {

    private Long educationId;
    private Long degreeId;
    private String institution;
    private Integer graduationYear;
    private Integer startingYear;

    // Constructors, getters, and setters
}
