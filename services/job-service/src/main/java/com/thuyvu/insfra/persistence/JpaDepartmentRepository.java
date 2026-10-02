package com.thuyvu.insfra.persistence;

import com.thuyvu.insfra.persistence.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDepartmentRepository extends JpaRepository<Department, UUID> {
}
