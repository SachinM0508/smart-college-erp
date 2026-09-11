package com.college.erp.repository;

import com.college.erp.entity.Admission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    Optional<Admission> findByStudentId(Long studentId);

    boolean existsByStudentId(Long studentId);
}