package com.GoldenGate.GoldenGate.system.repository;


import com.GoldenGate.GoldenGate.system.model.Degree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {

    @Query("SELECT d FROM Degree d WHERE LOWER(d.degreeName) LIKE LOWER(CONCAT('%', LOWER(:keyword), '%'))")
    List<Degree> findByKeywordIgnoreCase(@Param("keyword") String keyword);

}
