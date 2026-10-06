package com.thuyvu.mapper;

import com.thuyvu.dto.CandidateResponse;
import com.thuyvu.entity.Candidates;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    @Mapping(target = "skillIds", expression = "java(candidate.getCandidateSkills().stream().map(cs -> cs.getCandidateSkillId().getSkillId().getId()).toList())")
    CandidateResponse toDto(Candidates candidate);

}
