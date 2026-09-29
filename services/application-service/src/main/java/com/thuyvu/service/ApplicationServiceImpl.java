package com.thuyvu.service;

import com.thuyvu.client.JobServiceClient;
import com.thuyvu.dto.ApplicationResponse;
import com.thuyvu.dto.CreateApplicationRequest;
import com.thuyvu.entity.Application;
import com.thuyvu.entity.ApplicationStatus;
import com.thuyvu.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobServiceClient jobServiceClient;

    @Override
    public ApplicationResponse createApplication(CreateApplicationRequest request) {

        // 1. Gọi Job Service kiểm tra job có tồn tại không
        boolean jobExists = jobServiceClient.jobExists(request.jobId());
        if (!jobExists) {
            throw new IllegalArgumentException("Job not found with id: " + request.jobId());
        }

        // 2. Kiểm tra candidate đã apply cho job này chưa
        boolean alreadyApplied = applicationRepository
                .existsByJobIdAndCandidateId(request.jobId(), request.candidateId());
        if (alreadyApplied) {
            throw new IllegalArgumentException(
                    "Candidate " + request.candidateId() + " has already applied for job " + request.jobId());
        }

        // 3. Tạo Application entity
        Application application = Application.builder()
                .jobId(request.jobId())
                .candidateId(request.candidateId())
                .coverLetter(request.coverLetter())
                .resumeUrl(request.resumeUrl())
                .status(ApplicationStatus.SUBMITTED)
                .build();

        // 4. Lưu vào DB
        Application saved = applicationRepository.save(application);
        log.info("Created application {} for job {} by candidate {}",
                saved.getId(), saved.getJobId(), saved.getCandidateId());

        // 5. Trả về response
        return toResponse(saved);
    }

    private ApplicationResponse toResponse(Application app) {
        return new ApplicationResponse(
                app.getId(),
                app.getJobId(),
                app.getCandidateId(),
                app.getCoverLetter(),
                app.getResumeUrl(),
                app.getStatus().name(),
                app.getCreatedAt(),
                app.getUpdatedAt()
        );
    }
}
