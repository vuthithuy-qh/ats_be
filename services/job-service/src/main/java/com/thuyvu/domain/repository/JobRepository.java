package com.thuyvu.domain.repository;

import com.thuyvu.domain.aggregate.JobAggregate;

import java.util.Optional;
import java.util.UUID;

public interface JobRepository {
    JobAggregate save(JobAggregate aggregate);
    Optional<JobAggregate> findById(UUID jobId);
}
