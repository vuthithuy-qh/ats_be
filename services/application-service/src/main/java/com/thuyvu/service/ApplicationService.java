package com.thuyvu.service;

import com.thuyvu.dto.ApplicationDetailResponse;
import com.thuyvu.dto.ApplicationResponse;
import com.thuyvu.dto.CreateApplicationRequest;

import java.util.UUID;

public interface ApplicationService {
    ApplicationResponse createApplication(CreateApplicationRequest request);

    ApplicationDetailResponse getApplicationDetail(UUID applicationId);

}
