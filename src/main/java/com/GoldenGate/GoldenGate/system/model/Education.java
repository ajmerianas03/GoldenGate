package com.GoldenGate.GoldenGate.system.model;

import com.GoldenGate.GoldenGate.user.User;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "education")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educationId;

    private Long degreeId;

    private String institution;

    private Integer graduationYear;

    private Integer startingYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
