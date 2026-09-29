package com.thuyvu.controller;

import com.thuyvu.dto.CandidateRequest;
import com.thuyvu.dto.CandidateResponse;
import com.thuyvu.entity.Candidates;
import com.thuyvu.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createCandidate(@Valid @RequestBody CandidateRequest request) {
        candidateService.save(request);
        return ResponseEntity.ok(Map.of("message", "Create new success"));
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateResponse> getCandidateById(@PathVariable("candidateId") UUID candidateId) {
        CandidateResponse response = candidateService.getCandidateById(candidateId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Candidates> getCandidateByEmail(@RequestParam("email") String email) {
        Candidates candidate = candidateService.getCandidateByEmail(email);
        return ResponseEntity.ok(candidate);
    }

    @PatchMapping("/{candidateId}")
    public ResponseEntity<Map<String, String>> updateCandidate(
            @PathVariable("candidateId") UUID candidateId,
            @RequestBody CandidateRequest request) {
        candidateService.updateCandidate(candidateId, request);
        return ResponseEntity.ok(Map.of("message", "update success"));
    }

    @GetMapping("/{candidateId}/skills")
    public ResponseEntity<List<UUID>> getCandidateSkills(@PathVariable("candidateId") UUID candidateId) {
        List<UUID> skills = candidateService.getCandidateSkills(candidateId);
        return ResponseEntity.ok(skills);
    }
}