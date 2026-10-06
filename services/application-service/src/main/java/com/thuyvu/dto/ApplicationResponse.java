package com.thuyvu.dto;

import com.thuyvu.entity.ApplicationStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ApplicationResponse {
    private UUID id;

    private UUID jobId;

    private UUID candidateId;

    private UUID cvId;

    private UUID departmentId;

    private Long transferredFrom;

    private Long pipelineStageId;

    private ApplicationStatus status;

    private OffsetDateTime appliedAt;

}
