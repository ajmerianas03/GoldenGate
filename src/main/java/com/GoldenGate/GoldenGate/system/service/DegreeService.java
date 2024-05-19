package com.GoldenGate.GoldenGate.system.service;

import com.GoldenGate.GoldenGate.system.model.Comment;
import com.GoldenGate.GoldenGate.system.model.Degree;

import java.util.List;

public interface DegreeService {

    Degree saveDegree(Degree degree);

    Degree updateDegree(Long id, Degree updateddegree);

    boolean deleteDegree(Long id);

    List<Degree> getDegreeByKeword(String str);

}
