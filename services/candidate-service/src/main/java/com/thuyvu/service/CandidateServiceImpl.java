package com.thuyvu.service;

import com.thuyvu.dto.CandidateRequest;
import com.thuyvu.entity.CandidateSkillId;
import com.thuyvu.entity.CandidateSkills;
import com.thuyvu.entity.Candidates;
import com.thuyvu.entity.Skills;
import com.thuyvu.repository.CandidateRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Builder

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    @Override
    public Candidates save(CandidateRequest request) {
        Candidates candidate = toCandidate(request);



        return candidateRepository.save(candidate);
    }

    @Override
    public Candidates getCandidateById(UUID candidateId) {
        return candidateRepository.findById(candidateId).orElseThrow(() -> new RuntimeException("Khong tim thay ung vien"));
    }

    @Override
    public Candidates getCandidateByEmail(String email) {
        return candidateRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ứng viên với email: " + email));
    }

    @Override
    public Candidates updateCandidate(UUID candidateId, CandidateRequest request) {
        Candidates existingCandidate = getCandidateById(candidateId);

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

        return candidateRepository.save(existingCandidate);
    }

    @Override
    public List<UUID> getCandidateSkills(UUID candidateId) {
        Candidates candidate = getCandidateById(candidateId);

        return candidate.getCandidateSkills().stream()
                .map(candidateSkill -> candidateSkill.getCandidateSkillId().getSkillId().getId()).toList();

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
                .build();


        if (request.getSkillIds() != null && !request.getSkillIds().isEmpty()) {
            List<CandidateSkills> candidateSkillsList = request.getSkillIds().stream().map(id -> {
                Skills skill = new Skills();
                skill.setId(id);

                CandidateSkillId candidateSkillId = new CandidateSkillId();
                candidateSkillId.setId(candidate);
                candidateSkillId.setSkillId(skill);

                return CandidateSkills.builder()
                        .candidateSkillId(candidateSkillId)
                        .build();

            }).toList();

            candidate.setCandidateSkills(candidateSkillsList);
        }

        return candidate;
    }
}
