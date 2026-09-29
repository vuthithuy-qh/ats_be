package com.thuyvu.application.service;

import com.thuyvu.api.exception.JobNotFoundException;
import com.thuyvu.application.port.in.GetJobPort;
import com.thuyvu.domain.aggregate.JobAggregate;
import com.thuyvu.domain.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetJobUseCase implements GetJobPort {

    private final JobRepository jobRepository;

    @Override
    public JobAggregate execute(UUID jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new JobNotFoundException(
                        "Job not found with id: " + jobId));
    }
}
