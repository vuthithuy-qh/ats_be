package com.thuyvu.repository;

import com.thuyvu.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {
    boolean existsByJobIdAndCandidateId(UUID jobId, UUID candidateId);
}
