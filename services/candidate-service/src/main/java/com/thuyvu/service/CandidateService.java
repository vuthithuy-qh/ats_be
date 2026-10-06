package com.thuyvu.service;


import com.thuyvu.dto.CandidateRequest;
import com.thuyvu.dto.CandidateResponse;
import com.thuyvu.entity.Candidates;

import java.util.List;
import java.util.UUID;

public interface CandidateService {


    Candidates save(CandidateRequest request);

    CandidateResponse getCandidateById(UUID candidateId);

    CandidateResponse  getCandidateByEmail(String email);

    CandidateResponse  updateCandidate(UUID candidateId, CandidateRequest request);

    List<UUID> getCandidateSkills(UUID candidateId);
}
