package com.thuyvu.insfra.persistence;

import com.thuyvu.insfra.persistence.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaJobRepository extends JpaRepository<Job, UUID> {
}
