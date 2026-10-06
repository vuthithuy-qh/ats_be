package com.thuyvu.controller;

import com.thuyvu.dto.ApplicationDetailResponse;
import com.thuyvu.dto.ApplicationResponse;
import com.thuyvu.dto.CreateApplicationRequest;
import com.thuyvu.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;


    @PostMapping
    public ResponseEntity<ApplicationResponse> createApplication(
            @Valid @RequestBody CreateApplicationRequest request) {
        ApplicationResponse response = applicationService.createApplication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDetailResponse> getApplicationDetail(@PathVariable("id")UUID id){
        ApplicationDetailResponse response = applicationService.getApplicationDetail(id);
        return ResponseEntity.ok(response);
    }
}
