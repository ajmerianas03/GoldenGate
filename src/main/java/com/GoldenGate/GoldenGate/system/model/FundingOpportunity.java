package com.GoldenGate.GoldenGate.system.model;

import com.GoldenGate.GoldenGate.system.Enum.BusinessStage;
import com.GoldenGate.GoldenGate.system.Enum.FundingRound;
import com.GoldenGate.GoldenGate.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fundingopportunities")
public class FundingOpportunity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "opportunity_id")
    private Integer opportunityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "funding_amount")
    private double fundingAmount;

    @Column(name = "target_market")
    private String targetMarket;

    @Column(name = "business_model")
    private String businessModel;

    @Enumerated(EnumType.STRING)
    @Column(name = "business_stage")
    private BusinessStage businessStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "funding_round")
    private FundingRound fundingRound;

    @Column(name = "proposal")
    private String proposal;

    @Column(name = "pitch_deck_pdf",columnDefinition = "LONGTEXT")
    private String pitchDeckPdf;

    @Column(name = "business_plan_pdf",columnDefinition = "LONGTEXT")
    private String businessPlanPdf;

    @Column(name = "financial_statements_pdf",columnDefinition = "LONGTEXT")
    private String financialStatementsPdf;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // Getters and setters
}
