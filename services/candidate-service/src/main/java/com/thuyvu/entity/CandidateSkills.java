package com.thuyvu.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "candidate_skills")
@Builder
@Data
@AllArgsConstructor
public class CandidateSkills {

    @EmbeddedId
    private CandidateSkillId candidateSkillId;

    private LocalDate date;

    public CandidateSkills() {

    }
}
