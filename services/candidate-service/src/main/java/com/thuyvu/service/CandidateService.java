package com.thuyvu.service;


import com.thuyvu.dto.CandidateRequest;
import com.thuyvu.dto.CandidateResponse;
import com.thuyvu.entity.Candidates;

import java.util.List;
import java.util.UUID;

public interface CandidateService {


    Candidates save(CandidateRequest request);

    CandidateResponse getCandidateById(UUID candidateId);

    Candidates getCandidateByEmail(String email);

    Candidates updateCandidate(UUID candidateId, CandidateRequest request);

    List<UUID> getCandidateSkills(UUID candidateId);
}
