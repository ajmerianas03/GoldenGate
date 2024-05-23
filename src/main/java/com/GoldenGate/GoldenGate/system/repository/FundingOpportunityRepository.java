package com.GoldenGate.GoldenGate.system.repository;

import com.GoldenGate.GoldenGate.system.model.FundingOpportunity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingOpportunityRepository extends JpaRepository<FundingOpportunity, Integer> {
    Page<FundingOpportunity> findByUser_UserId(Integer userId, Pageable pageable);
    // You can add custom query methods here if needed
}
