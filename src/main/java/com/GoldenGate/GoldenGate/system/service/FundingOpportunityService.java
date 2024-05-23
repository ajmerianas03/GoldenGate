package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.DTO.FundingOpportunityDTO;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FundingOpportunityService {
//    FundingOpportunityDTO saveFundingOpportunity(FundingOpportunityDTO fundingOpportunityDTO, User user);
//    FundingOpportunityDTO updateFundingOpportunity(int opportunityId, FundingOpportunityDTO fundingOpportunityDTO, User user);
//    void deleteFundingOpportunity(int opportunityId);
//    List<FundingOpportunityDTO> getAllFundingOpportunities();
//    FundingOpportunityDTO getFundingOpportunityById(int opportunityId);
//    // Add more methods as needed

    FundingOpportunityDTO saveFundingOpportunity(FundingOpportunityDTO fundingOpportunityDTO, User user);
    FundingOpportunityDTO updateFundingOpportunity(int opportunityId, FundingOpportunityDTO fundingOpportunityDTO, User user);
    void deleteFundingOpportunity(int opportunityId);
    Page<FundingOpportunityDTO> getAllFundingOpportunities(int page, int size);
    Page<FundingOpportunityDTO> getFundingOpportunitiesByUserId(Integer userId, int page, int size);


}
