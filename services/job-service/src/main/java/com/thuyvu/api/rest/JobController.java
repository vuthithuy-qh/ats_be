package com.thuyvu.api.rest;

import com.thuyvu.api.dto.CreateJobRequest;
import com.thuyvu.api.dto.JobResponse;
import com.thuyvu.api.mapper.JobMapper;
import com.thuyvu.application.port.in.CreateJobPort;
import com.thuyvu.application.port.in.GetJobPort;
import com.thuyvu.domain.aggregate.JobAggregate;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
public class JobController {

    private final CreateJobPort createJobPort;
    private final GetJobPort getJobPort;

    
    @PostMapping
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody CreateJobRequest request) {
        JobAggregate created = createJobPort.execute(JobMapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(JobMapper.toResponse(created));
    }

    
    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable("jobId") UUID jobId) {
        JobAggregate job = getJobPort.execute(jobId);
        return ResponseEntity.ok(JobMapper.toResponse(job));
    }
}
