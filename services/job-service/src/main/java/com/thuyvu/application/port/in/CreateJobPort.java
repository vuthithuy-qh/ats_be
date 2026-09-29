package com.thuyvu.application.port.in;

import com.thuyvu.application.comman.JobCommand;
import com.thuyvu.domain.aggregate.JobAggregate;

public interface CreateJobPort {
    JobAggregate execute(JobCommand jobCommand);
}
