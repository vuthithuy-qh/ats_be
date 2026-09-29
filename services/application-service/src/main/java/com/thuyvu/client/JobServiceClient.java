package com.thuyvu.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Client gọi Job Service để kiểm tra job tồn tại.
 * GET /api/v1/jobs/{jobId}
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JobServiceClient {

    private final RestTemplate restTemplate;

    @Value("${job-service.url}")
    private String jobServiceUrl;

    /**
     * Kiểm tra job có tồn tại không bằng cách gọi GET /api/v1/jobs/{jobId}
     *
     * @param jobId UUID của job cần kiểm tra
     * @return true nếu job tồn tại (HTTP 200), false nếu 404
     * @throws RuntimeException nếu có lỗi khác (500, connection error, ...)
     */
    public boolean jobExists(UUID jobId) {
        String url = jobServiceUrl + "/api/v1/jobs/" + jobId;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound e) {
            log.warn("Job not found: {}", jobId);
            return false;
        } catch (Exception e) {
            log.error("Error calling job-service for jobId={}: {}", jobId, e.getMessage());
            throw new RuntimeException("Failed to verify job existence. Job service may be unavailable.", e);
        }
    }
}
