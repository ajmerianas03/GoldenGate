package com.GoldenGate.GoldenGate.system.DTO;

import com.GoldenGate.GoldenGate.system.Enum.BusinessStage;
import com.GoldenGate.GoldenGate.system.Enum.FundingRound;
import lombok.Data;

@Data
public class FundingOpportunityDTO {

    private Integer opportunityId;
    private String contactEmail;
    private double fundingAmount;
    private String targetMarket;
    private String businessModel;
    private BusinessStage businessStage;
    private FundingRound fundingRound;
    private String proposal;
    private String pitchDeckPdf;
    private String businessPlanPdf;
    private String financialStatementsPdf;
    private int likeCount;
}
