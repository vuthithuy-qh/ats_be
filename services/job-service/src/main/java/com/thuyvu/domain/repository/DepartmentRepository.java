package com.thuyvu.domain.repository;

import java.util.UUID;

public interface DepartmentRepository {
    boolean existById(UUID departmentId); 
    
}
