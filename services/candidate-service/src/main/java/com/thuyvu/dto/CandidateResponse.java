package com.thuyvu.dto;

import com.thuyvu.entity.CandidateStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CandidateResponse {
    private UUID id;
    private String fullName;
    private String email;
    private String phone;
    private String source;
    private CandidateStatus status;
    private List<UUID> skillIds;
}
