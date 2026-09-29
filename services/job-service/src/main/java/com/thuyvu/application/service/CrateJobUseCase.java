package com.thuyvu.application.service;

import com.thuyvu.application.comman.JobCommand;
import com.thuyvu.application.port.in.CreateJobPort;
import com.thuyvu.domain.aggregate.JobAggregate;
import com.thuyvu.domain.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrateJobUseCase implements CreateJobPort {

    private final JobRepository jobRepository;

    @Override
    public JobAggregate execute(JobCommand command) {
        if (command == null) {
            throw new IllegalArgumentException("Job command must not be null");
        }

        JobAggregate aggregate = JobAggregate.createDraft(
                null,
                command.title(),
                command.description(),
                command.departmentId(),
                command.recruiterId(),
                command.location(),
                command.employmentType(),
                command.workMode(),
                command.salaryMin(),
                command.salaryMax(),
                command.currency(),
                command.applicationDeadline(),
                command.skillIds()
        );

        return jobRepository.save(aggregate);
    }
}
