package com.thuyvu.api.mapper;

import com.thuyvu.api.dto.CreateJobRequest;
import com.thuyvu.api.dto.JobResponse;
import com.thuyvu.application.comman.JobCommand;
import com.thuyvu.domain.aggregate.JobAggregate;

public class JobMapper {

    private JobMapper() {
    }

    public static JobCommand toCommand(CreateJobRequest req) {
        return new JobCommand(
                req.title(),
                req.description(),
                req.departmentId(),
                req.recruiterId(),
                req.location(),
                req.employmentType(),
                req.workMode(),
                req.salaryMin(),
                req.salaryMax(),
                req.currency(),
                req.applicationDeadline(),
                req.skillIds()
        );
    }

    public static JobResponse toResponse(JobAggregate agg) {
        return new JobResponse(
                agg.getId(),
                agg.getTitle(),
                agg.getDescription(),
                null,  // companyId - not available in aggregate
                null,  // recruiterId mapped as UUID in response but Long in aggregate
                agg.getLocation(),
                agg.getEmploymentType(),
                agg.getWorkMode(),
                agg.getSalaryRange().min(),
                agg.getSalaryRange().max(),
                agg.getCurrency(),
                agg.getStatus().name(),
                agg.getDeadline(),
                agg.getSkillIds(),
                null,  // createdAt
                null   // updatedAt
        );
    }
}
