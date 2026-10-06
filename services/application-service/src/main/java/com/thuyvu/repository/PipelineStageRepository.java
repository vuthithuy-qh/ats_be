package com.thuyvu.repository;

import com.thuyvu.entity.PipelineStage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PipelineStageRepository extends JpaRepository<PipelineStage, Long> {

    public List<PipelineStage> findByDefaultStage(boolean defaultStage);
}
