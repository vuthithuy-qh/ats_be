package com.thuyvu.service;

import com.thuyvu.dto.CandidateRequest;
import com.thuyvu.dto.CandidateResponse;
import com.thuyvu.entity.*;
import com.thuyvu.mapper.CandidateMapper;
import com.thuyvu.repository.CandidateRepository;
import com.thuyvu.repository.CandidateSkillsRepository;
import com.thuyvu.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateSkillsRepository candidateSkillsRepository;
    private final SkillRepository skillsRepository;
    private final CandidateMapper candidateMapper;

    @Override
    @Transactional
    public Candidates save(CandidateRequest request) {
        Candidates savedCandidate = candidateRepository.save(toCandidate(request));
        List<UUID> requestedSkillIds = request.getSkillIds() == null ? List.of() : request.getSkillIds();

        Set<UUID> uniqueSkillIds = new LinkedHashSet<>(requestedSkillIds);
        if(uniqueSkillIds.isEmpty()){
            return savedCandidate;
        }

        List<Skills> skills = skillsRepository.findAllById(uniqueSkillIds);
        if(skills.size() != uniqueSkillIds.size()){
            throw new IllegalArgumentException("One or More skill Ids do not exist");
        }

        List<CandidateSkills> candidateSkills = skills.stream()
                .map(skill -> CandidateSkills.builder()
                        .candidateSkillId(new CandidateSkillId(savedCandidate, skill))
                        .date(LocalDate.now())
                        .build()).toList();

        candidateSkillsRepository.saveAll(candidateSkills);

        return savedCandidate;
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getCandidateById(UUID candidateId) {

         Candidates candidate = candidateRepository.findById(candidateId).orElseThrow(()
                 -> new RuntimeException("Khong tim thay ung vien"));

         return candidateMapper.toDto(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse  getCandidateByEmail(String email) {
        Candidates candidate =  candidateRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ứng viên với email: " + email));
        return candidateMapper.toDto(candidate);
    }

    @Override
    public CandidateResponse updateCandidate(UUID candidateId, CandidateRequest request) {
        Candidates existingCandidate = candidateRepository.findById(candidateId).orElseThrow(()-> new RuntimeException("ko tim thay "));

        if(request.getSkillIds() != null){
            existingCandidate.getCandidateSkills().clear();

            List<CandidateSkills> newSkills = request.getSkillIds().stream().map(skillId -> {
                Skills skill = new Skills();
                skill.setId(skillId);

                CandidateSkillId candidateSkillId = new CandidateSkillId();
                candidateSkillId.setId(existingCandidate);
                candidateSkillId.setSkillId(skill);

                return CandidateSkills.builder()
                        .candidateSkillId(candidateSkillId)
                        .build();
            }).toList();
            existingCandidate.getCandidateSkills().addAll(newSkills);
        }

        Candidates candidate =  candidateRepository.save(existingCandidate);
        return candidateMapper.toDto(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UUID> getCandidateSkills(UUID candidateId) {
        Candidates candidate = candidateRepository.findById(candidateId).orElseThrow(()-> new RuntimeException("ko tim thay "));

        return candidate.getCandidateSkills().stream()
                .map(candidateSkill -> candidateSkill.getCandidateSkillId().getSkillId().getId()).toList();

    }

    private CandidateResponse toCandidateResponse(Candidates candidate){
        CandidateResponse candidateResponse = CandidateResponse.builder()
                .fullName(candidate.getFullName())
                .email(candidate.getEmail())
                .phone(candidate.getPhone())
                .source(candidate.getSource())
                .build();

        return  candidateResponse;
    }

    private Candidates toCandidate(CandidateRequest request) {
        // 1. Build đối tượng Candidates trước (chưa có danh sách skills)
        Candidates candidate = Candidates.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .source(request.getSource())
                .utmSource(request.getUtmSource())
                .utmMedium(request.getUtmMedium())
                .utmCampaign(request.getUtmCampaign())
                .status(CandidateStatus.ACTIVE)
                .build();


//        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
//            List<CandidateSkills> candidateSkillsList = request.getSkillIds().stream().map(id -> {
//                Skills skill = new Skills();
//                skill.setId(id);
//
//                CandidateSkillId candidateSkillId = new CandidateSkillId();
//                candidateSkillId.setId(candidate);
//                candidateSkillId.setSkillId(skill);
//
//                return CandidateSkills.builder()
//                        .candidateSkillId(candidateSkillId)
//                        .build();
//
//            }).toList();
//
//            candidate.setCandidateSkills(candidateSkillsList);
//        }

        return candidate;
    }
}
