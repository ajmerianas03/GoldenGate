package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.system.model.Degree;
import com.GoldenGate.GoldenGate.system.service.DegreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController

@RequestMapping("/api/v1/degrees")
public class DegreeController {

    private final DegreeService degreeService;

    @Autowired
    public DegreeController(DegreeService degreeService) {
        this.degreeService = degreeService;
    }

    @PostMapping
    public ResponseEntity<Degree> saveDegree(@RequestBody Degree degree) {
        Degree savedDegree = degreeService.saveDegree(degree);
        return new ResponseEntity<>(savedDegree, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Degree> updateDegree(@PathVariable Long id, @RequestBody Degree updatedDegree) {
        Degree degree = degreeService.updateDegree(id, updatedDegree);
        return new ResponseEntity<>(degree, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDegree(@PathVariable Long id) {
        degreeService.deleteDegree(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Degree>> getDegreeByKeyword(@RequestParam("keyword") String keyword) {
        List<Degree> degrees = degreeService.getDegreeByKeword(keyword);
        return new ResponseEntity<>(degrees, HttpStatus.OK);
    }
}
