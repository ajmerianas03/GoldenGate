package com.GoldenGate.GoldenGate.system.controller;

import com.GoldenGate.GoldenGate.system.model.UserSkill;
import com.GoldenGate.GoldenGate.system.service.UserSkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/userskills")
@RequiredArgsConstructor
public class UserSkillController {

    private final UserSkillService userSkillService;

    @PostMapping("")
    public ResponseEntity<UserSkill> saveUserSkill(@RequestBody UserSkill userSkill) {
        UserSkill savedUserSkill = userSkillService.saveUserSkill(userSkill);
        return new ResponseEntity<>(savedUserSkill, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserSkill> getUserSkillById(@PathVariable Long id) {
        UserSkill userSkill = userSkillService.getUserSkillById(id);
        if (userSkill != null) {
            return ResponseEntity.ok(userSkill);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserSkill(@PathVariable Long id) {
        userSkillService.deleteUserSkill(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSkill> updateUserSkillById(@PathVariable Long id, @RequestBody UserSkill updatedUserSkill) {
        UserSkill updatedSkill = userSkillService.updateUserSkillById(id, updatedUserSkill);
        if (updatedSkill != null) {
            return ResponseEntity.ok(updatedSkill);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
