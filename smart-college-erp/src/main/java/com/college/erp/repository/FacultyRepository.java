package com.college.erp.repository;

import com.college.erp.entity.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    boolean existsByFacultyId(String facultyId);

    boolean existsByAadhar(String aadhar);

    boolean existsByEmployeeCode(String employeeCode);

    boolean existsByFacultyIdAndIdNot(String facultyId, Long id);

    boolean existsByAadharAndIdNot(String aadhar, Long id);

    boolean existsByEmployeeCodeAndIdNot(String employeeCode, Long id);

    Optional<Faculty> findByUserId(Long userId);
}