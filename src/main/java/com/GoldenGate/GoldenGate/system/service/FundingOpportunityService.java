package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.DTO.FundingOpportunityDTO;
import com.GoldenGate.GoldenGate.user.User;

import java.util.List;

public interface FundingOpportunityService {
    FundingOpportunityDTO saveFundingOpportunity(FundingOpportunityDTO fundingOpportunityDTO, User user);
    FundingOpportunityDTO updateFundingOpportunity(int opportunityId, FundingOpportunityDTO fundingOpportunityDTO, User user);
    void deleteFundingOpportunity(int opportunityId);
    List<FundingOpportunityDTO> getAllFundingOpportunities();
    FundingOpportunityDTO getFundingOpportunityById(int opportunityId);
    // Add more methods as needed
}
