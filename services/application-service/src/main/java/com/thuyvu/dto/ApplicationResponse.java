package com.thuyvu.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ApplicationResponse(
        UUID id,
        UUID jobId,
        UUID candidateId,
        String coverLetter,
        String resumeUrl,
        String status,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
