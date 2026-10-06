package com.thuyvu.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateView {
    private UUID id;
    private String fullName;
    private String email;
    private CandidateStatus status;
}
