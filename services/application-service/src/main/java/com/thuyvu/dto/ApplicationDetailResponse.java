package com.thuyvu.dto;

import com.thuyvu.entity.ApplicationStatus;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationDetailResponse {

    private UUID applicationId;
    private ApplicationStatus status;
    private OffsetDateTime appliedAt;
    private String cvUrl;

    private CandidateView candidate;
    private JobView job;

    private String currentStageName;
}
