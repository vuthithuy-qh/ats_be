package com.thuyvu.repository;

import com.thuyvu.entity.Skills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SkillRepository extends JpaRepository<Skills, UUID> {
}
