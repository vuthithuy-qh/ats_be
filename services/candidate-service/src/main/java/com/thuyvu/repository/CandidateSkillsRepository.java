package com.thuyvu.repository;

import com.thuyvu.entity.CandidateSkillId;
import com.thuyvu.entity.CandidateSkills;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSkillsRepository extends JpaRepository<CandidateSkills, CandidateSkillId> {
}
