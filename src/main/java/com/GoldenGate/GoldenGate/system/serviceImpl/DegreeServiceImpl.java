package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.model.Degree;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.repository.DegreeRepository;
import com.GoldenGate.GoldenGate.system.service.DegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DegreeServiceImpl implements DegreeService {

    private final DegreeRepository degreeRepository;

    @Autowired
    public DegreeServiceImpl(DegreeRepository degreeRepository) {
        this.degreeRepository = degreeRepository;
    }

    @Override
    public Degree saveDegree(Degree degree) {

        Degree saveDegree=degreeRepository.save(degree);
        return saveDegree;
    }

    @Override
    public Degree updateDegree(Long id, Degree updateddegree) {
        // existingDegree = degreeRepository.findById(id);
        Degree existingDegree = degreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if(existingDegree != null){
            existingDegree.setDegreeDuration(updateddegree.getDegreeDuration());
            existingDegree.setDegreeName(updateddegree.getDegreeName());
            Degree saveDegree=degreeRepository.save(existingDegree);
        }

        return existingDegree;
    }

    @Override
    public boolean deleteDegree(Long id) {
        Degree existingDegree = degreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if(existingDegree != null){
            degreeRepository.delete(existingDegree);
        }

    return true;
    }

    @Override
    public List<Degree> getDegreeByKeword(String str) {
        List<Degree> findByKeyword =  degreeRepository.findByKeywordIgnoreCase(str);
        return findByKeyword;
    }
}
