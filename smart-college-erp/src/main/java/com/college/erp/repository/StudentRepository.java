package com.college.erp.repository;

import com.college.erp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByStudentId(String studentId);

    boolean existsByStudentId(String studentId);

    boolean existsByAadhar(String aadhar);

    boolean existsByApaarId(String apaarId);
}