package com.thuyvu.controller;

import com.thuyvu.dto.ApplicationResponse;
import com.thuyvu.dto.CreateApplicationRequest;
import com.thuyvu.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * POST /api/v1/applications - Tạo application mới
     * Bước 1: Gọi job-service GET /api/v1/jobs/{jobId} để kiểm tra job tồn tại
     * Bước 2: Lưu application vào DB
     */
    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid @RequestBody CreateApplicationRequest request) {
        ApplicationResponse response = applicationService.createApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
