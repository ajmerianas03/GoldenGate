package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.FundingOpportunityDTO;
import com.GoldenGate.GoldenGate.system.model.FundingOpportunity;
import com.GoldenGate.GoldenGate.system.repository.FundingOpportunityRepository;
import com.GoldenGate.GoldenGate.system.service.FundingOpportunityService;
import com.GoldenGate.GoldenGate.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<FundingOpportunityDTO> getAllFundingOpportunities() {
        List<FundingOpportunity> fundingOpportunities = fundingOpportunityRepository.findAll();
        return fundingOpportunities.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public FundingOpportunityDTO getFundingOpportunityById(int opportunityId) {
        Optional<FundingOpportunity> optionalFundingOpportunity = fundingOpportunityRepository.findById(opportunityId);
        return optionalFundingOpportunity.map(this::convertToDTO).orElse(null);
    }

    private FundingOpportunityDTO convertToDTO(FundingOpportunity fundingOpportunity) {
        FundingOpportunityDTO dto = new FundingOpportunityDTO();
        dto.setOpportunityId(fundingOpportunity.getOpportunityId());
        // Set other fields from entity to DTO
        return dto;
    }
}
