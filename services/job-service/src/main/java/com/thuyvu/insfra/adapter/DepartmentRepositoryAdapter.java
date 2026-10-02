package com.thuyvu.insfra.adapter;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.thuyvu.domain.repository.DepartmentRepository;
import com.thuyvu.insfra.persistence.JpaDepartmentRepository;

import lombok.RequiredArgsConstructor;

@Repository 
@RequiredArgsConstructor 
public class DepartmentRepositoryAdapter implements DepartmentRepository {
    
    private final JpaDepartmentRepository jpaDepartmentRepository;

    @Override
    public boolean existById(UUID departmentId) {
        return jpaDepartmentRepository.existsById(departmentId);
    }
}
