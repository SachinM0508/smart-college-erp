package com.college.erp.repository;

import com.college.erp.entity.FacultyPayroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyPayrollRepository
        extends JpaRepository<FacultyPayroll, Long> {

    boolean existsByFacultyId(Long facultyId);

    boolean existsByFacultyIdAndIdNot(Long facultyId, Long id);

    boolean existsByPanNumber(String panNumber);

    boolean existsByPanNumberAndIdNot(String panNumber, Long id);
}