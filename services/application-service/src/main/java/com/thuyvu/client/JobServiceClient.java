package com.thuyvu.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Client gọi Job Service để kiểm tra job tồn tại.
 * GET /api/v1/jobs/{jobId}
**/

@FeignClient(name = "job-service", url= "${job-service.url}")
public interface JobServiceClient {

    @GetMapping ("/api/v1/jobs/{jobId}")
    String getJobById(@PathVariable ("jobId") UUID jobId);

    
} 
