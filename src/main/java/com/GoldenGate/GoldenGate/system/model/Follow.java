package com.GoldenGate.GoldenGate.system.model;

import com.GoldenGate.GoldenGate.system.Enum.FollowStatus;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.persistence.*;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "follows")
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Integer followId;

    @ManyToOne
    @JoinColumn(name = "follower_id", referencedColumnName = "user_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id", referencedColumnName = "user_id")
    private User following;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "enum('pending','accepted','not_followed') DEFAULT 'not_followed'")
    private FollowStatus status;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
