package com.thuyvu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateApplicationRequest(
        @NotNull UUID jobId,
        @NotNull UUID candidateId,
        String coverLetter,
        @NotBlank String resumeUrl
) {
}
