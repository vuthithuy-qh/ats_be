package com.thuyvu.mapper;

import com.thuyvu.dto.CandidateResponse;
import com.thuyvu.entity.Candidates;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CandidateMapper {

    CandidateResponse toDto(Candidates candidate);

}
