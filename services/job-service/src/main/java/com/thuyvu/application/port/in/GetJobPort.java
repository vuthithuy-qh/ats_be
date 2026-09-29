package com.thuyvu.application.port.in;

import com.thuyvu.domain.aggregate.JobAggregate;

import java.util.UUID;

public interface GetJobPort {
    JobAggregate execute(UUID jobId);
}
