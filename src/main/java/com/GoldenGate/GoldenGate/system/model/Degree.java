package com.GoldenGate.GoldenGate.system.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "degree")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Degree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long degreeId;

    @Column(nullable = false)
    private Integer degreeDuration;

    @Column(nullable = false, length = 255)
    private String degreeName;
}
