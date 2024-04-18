package com.GoldenGate.GoldenGate.system.model;


import com.GoldenGate.GoldenGate.system.Enum.ProfileType;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "profile")
@Getter
@Setter
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;

    @Lob
    private byte[] avatar;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(nullable = false, length = 255)
    private String fullName;

    @Column(columnDefinition = "TEXT")
    private String otherDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private byte[] backgroundImage;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('Investor', 'Advisor', 'Startup', 'Company', 'User', 'Institute', 'Super Admin', 'Moderator', 'Support Admin')")
    private ProfileType profileType;


}