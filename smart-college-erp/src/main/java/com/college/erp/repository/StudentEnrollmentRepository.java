package com.college.erp.repository;

import com.college.erp.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEnrollmentRepository
        extends JpaRepository<StudentEnrollment, Long> {
}