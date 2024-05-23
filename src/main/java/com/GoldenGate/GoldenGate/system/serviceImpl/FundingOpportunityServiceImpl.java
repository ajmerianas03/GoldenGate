package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.FundingOpportunityDTO;
import com.GoldenGate.GoldenGate.system.model.FundingOpportunity;
import com.GoldenGate.GoldenGate.system.repository.FundingOpportunityRepository;
import com.GoldenGate.GoldenGate.system.service.FundingOpportunityService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FundingOpportunityServiceImpl implements FundingOpportunityService {

    private final FundingOpportunityRepository fundingOpportunityRepository;

    @Autowired
    public FundingOpportunityServiceImpl(FundingOpportunityRepository fundingOpportunityRepository) {
        this.fundingOpportunityRepository = fundingOpportunityRepository;
    }

    @Override
    public FundingOpportunityDTO saveFundingOpportunity(FundingOpportunityDTO fundingOpportunityDTO, User user) {
        FundingOpportunity fundingOpportunity = new FundingOpportunity();
        fundingOpportunity.setUser(user);

        // Set other fields here from DTO

        // Set default values or handle logic for other fields

        // Save and return DTO
        return convertToDTO(fundingOpportunityRepository.save(fundingOpportunity));
    }

    @Override
    public FundingOpportunityDTO updateFundingOpportunity(int opportunityId, FundingOpportunityDTO fundingOpportunityDTO, User user) {
        Optional<FundingOpportunity> optionalFundingOpportunity = fundingOpportunityRepository.findById(opportunityId);
        if (optionalFundingOpportunity.isPresent()) {
            FundingOpportunity fundingOpportunity = optionalFundingOpportunity.get();
            // Update fields from DTO
            fundingOpportunity.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            // Save and return DTO
            return convertToDTO(fundingOpportunityRepository.save(fundingOpportunity));
        }
        return null; // Handle if opportunity with given ID is not found
    }

    @Override
    public void deleteFundingOpportunity(int opportunityId) {
        fundingOpportunityRepository.deleteById(opportunityId);
    }

    @Override
    public Page<FundingOpportunityDTO> getAllFundingOpportunities(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FundingOpportunity> fundingOpportunityPage = fundingOpportunityRepository.findAll(pageable);
        return fundingOpportunityPage.map(this::convertToFundingOpportunityDTO);
    }



    private FundingOpportunityDTO convertToDTO(FundingOpportunity fundingOpportunity) {
        FundingOpportunityDTO dto = new FundingOpportunityDTO();
        dto.setOpportunityId(fundingOpportunity.getOpportunityId());
        // Set other fields from entity to DTO
        return dto;
    }

    @Override
    public Page<FundingOpportunityDTO> getFundingOpportunitiesByUserId(Integer userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FundingOpportunity> fundingOpportunityPage = fundingOpportunityRepository.findByUser_UserId(userId, pageable);
        return fundingOpportunityPage.map(this::convertToFundingOpportunityDTO);
    }

    private FundingOpportunityDTO convertToFundingOpportunityDTO(FundingOpportunity fundingOpportunity) {
        FundingOpportunityDTO dto = new FundingOpportunityDTO();
        dto.setOpportunityId(fundingOpportunity.getOpportunityId());
        dto.setContactEmail(fundingOpportunity.getContactEmail());
        dto.setFundingAmount(fundingOpportunity.getFundingAmount());
        dto.setTargetMarket(fundingOpportunity.getTargetMarket());
        dto.setBusinessModel(fundingOpportunity.getBusinessModel());
        dto.setBusinessStage(fundingOpportunity.getBusinessStage());
        dto.setFundingRound(fundingOpportunity.getFundingRound());
        dto.setProposal(fundingOpportunity.getProposal());
        dto.setPitchDeckPdf(fundingOpportunity.getPitchDeckPdf());
        dto.setBusinessPlanPdf(fundingOpportunity.getBusinessPlanPdf());
        dto.setFinancialStatementsPdf(fundingOpportunity.getFinancialStatementsPdf());
        dto.setLikeCount(fundingOpportunity.getLikeCount());
        return dto;
    }
}
