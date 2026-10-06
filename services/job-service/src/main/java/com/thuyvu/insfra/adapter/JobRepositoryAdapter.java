package com.thuyvu.insfra.adapter;

import com.thuyvu.domain.aggregate.JobAggregate;
import com.thuyvu.domain.model.JobStatus;
import com.thuyvu.domain.repository.JobRepository;
import com.thuyvu.insfra.persistence.JpaDepartmentRepository;
import com.thuyvu.insfra.persistence.JpaJobRepository;
import com.thuyvu.insfra.persistence.entity.Department;
import com.thuyvu.insfra.persistence.entity.Job;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JobRepositoryAdapter implements JobRepository {

    private final JpaJobRepository jpaJobRepository;
    private final JpaDepartmentRepository jpaDepartmentRepository;// ịnect data spring JPA

    @Override
    public JobAggregate save(JobAggregate aggregate) {
        Department department = jpaDepartmentRepository.findById(aggregate.getDepartmentId())
                .orElseThrow();

        Job entity = toEntity(aggregate, department);

        Job saved = jpaJobRepository.save(entity); // @PrePersist chạy ở đây, set createdAt
        return toAggregate(saved); // doc createdAt tu entity vua luu
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobAggregate> findById(UUID jobId) {
        return jpaJobRepository.findById(jobId)
                .map(this::toAggregate);
    }



    private Job toEntity(JobAggregate agg, Department dept) {
        return Job.builder()
                .id(agg.getId())
                .title(agg.getTitle())
                .description(agg.getDescription())
                .department(dept)
                .recruiterId(agg.getRecruiterId())
                .location(agg.getLocation())
                .employmentType(agg.getEmploymentType())
                .workMode(agg.getWorkMode())
                .salaryMin(agg.getSalaryRange().min())
                .salaryMax(agg.getSalaryRange().max())
                .currency(agg.getCurrency())
                .deadline(agg.getDeadline())
                .status(
                        com.thuyvu.insfra.persistence.entity.JobStatus.valueOf(agg.getStatus().name())
                )
                .build();
    }

    private JobAggregate toAggregate(Job entity) {
        return JobAggregate.reconstitute(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getDepartment().getId(),
                entity.getRecruiterId(),
                entity.getLocation(),
                entity.getEmploymentType(),
                entity.getWorkMode(),
                entity.getSalaryMin(),
                entity.getSalaryMax(),
                entity.getCurrency(),
                entity.getDeadline(),
                List.of(),
                JobStatus.valueOf(entity.getStatus().name()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
