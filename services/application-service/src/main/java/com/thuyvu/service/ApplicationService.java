package com.thuyvu.service;

import com.thuyvu.dto.ApplicationResponse;
import com.thuyvu.dto.CreateApplicationRequest;

public interface ApplicationService {
    ApplicationResponse createApplication(CreateApplicationRequest request);
}
