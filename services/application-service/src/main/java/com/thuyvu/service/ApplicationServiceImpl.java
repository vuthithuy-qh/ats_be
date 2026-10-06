package com.thuyvu.service;

import com.thuyvu.client.candidate.CandidateClient;
import com.thuyvu.client.job.JobClient;
import com.thuyvu.dto.*;
import com.thuyvu.entity.Application;
import com.thuyvu.entity.ApplicationStatus;
import com.thuyvu.entity.PipelineStage;
import com.thuyvu.exception.BusinessException;
import com.thuyvu.repository.ApplicationRepository;

import com.thuyvu.repository.PipelineStageRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final PipelineStageRepository pipelineStageRepository;
    private final JobClient jobClient;
    private final CandidateClient candidateClient;


    @Override
    public ApplicationResponse createApplication(CreateApplicationRequest request) {
        UUID candidateId = request.getCandidateId();
        UUID jobId = request.getJobId();


        if(applicationRepository.existsByJobIdAndCandidateId(jobId, candidateId)){
            throw new BusinessException(1, "Application already exists for this candidate and job");
        }

        JobView jobView;
        try {
            jobView = jobClient.findById(jobId);
        } catch (FeignException.NotFound e) {
            throw new BusinessException(6, "Job not found");
        }

        if(jobView.getApplicationDeadline().isBefore(LocalDate.now())){
            throw new BusinessException(2, "Application deadline is expired");
        }

        CandidateView candidate;
        try {
            candidate = candidateClient.findById(candidateId);
        } catch (feign.FeignException.NotFound e) {
            throw new BusinessException(4, "Candidate not found");
        }

        if(candidate.getStatus() != CandidateStatus.ACTIVE){
            throw new BusinessException(3, "Candidate is not ACTIVE, cannot apply");
        }
        log.info("Creating application for request{}", jobView);
        List<PipelineStage> pipelineStages = pipelineStageRepository.findByDefaultStage(true);

        PipelineStage defaultStage = pipelineStages.stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException(5, "No default pipeline stage configured"));



        // 3. Tạo Application entity
        Application application = new Application();
        application.setJobId(jobId);
        application.setCandidateId(candidateId);
        application.setStatus(ApplicationStatus.SUBMITTED);
        application.setAppliedAt(OffsetDateTime.now());
        application.setPipelineStage(defaultStage);

        //ramdom  tam để test
        application.setCvId(UUID.randomUUID());

        Application savedApplication = applicationRepository.save(application);

        log.info("Created application {} for job {} by candidate {}",
                savedApplication.getId(), savedApplication.getJobId(), savedApplication.getCandidateId());

        // 5. Trả về response
        return toResponse(savedApplication);
    }

    @Override
    public ApplicationDetailResponse getApplicationDetail(UUID applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(()-> new BusinessException(10, "Application not found"));

        //Goi dong bo synch call sang cac service khac de lay chi tiet
        JobView jobDetail = null;

        try {
            jobDetail = jobClient.findById(application.getJobId());

        }catch (Exception e){
            log.warn("Could not fetch job detail for jobId: {}", application.getJobId());
        }

        CandidateView candidateDetail = null;
        try{
            candidateDetail = candidateClient.findById(application.getCandidateId());
        }catch (Exception e){
            log.warn("Could not fetch candidate detail for candidateId: {}", application.getCandidateId());
        }

        return ApplicationDetailResponse.builder()
                .applicationId(application.getId())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .candidate(candidateDetail)
                .job(jobDetail)
                .currentStageName(application.getPipelineStage() != null ? application.getPipelineStage().getStageName() : null)
                .build();
    }

    private ApplicationResponse toResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .jobId(application.getJobId())
                .candidateId(application.getCandidateId())
                .cvId(application.getCvId())
                .departmentId(application.getDepartmentId())
                .transferredFrom(application.getTransferredFrom())
                .pipelineStageId(application.getPipelineStage() != null ? application.getPipelineStage().getId() : null)
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }
}
