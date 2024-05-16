package com.GoldenGate.GoldenGate.system.model;


import com.GoldenGate.GoldenGate.system.Enum.ProfileType;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;

    @Column(columnDefinition = "LONGTEXT")
    private String avatar;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false, length = 255)
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String otherDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "LONGTEXT")
    private String backgroundImage;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Investor', 'Advisor', 'Startup', 'Company', 'User', 'Institute', 'Super Admin', 'Moderator', 'Support Admin')")
    private ProfileType profileType;


}
