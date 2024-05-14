package com.GoldenGate.GoldenGate.system.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "industries")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Industry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "industry_id")
    private Long id;

    @Column(name = "industry_name", nullable = false)
    private String industryName;

    // Constructors, getters, and setters
}

