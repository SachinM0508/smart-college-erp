package com.college.erp.repository;

import com.college.erp.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SemesterRepository extends JpaRepository<Semester, Long> {

    boolean existsByCourseIdAndSemesterNumber(
            Long courseId,
            Integer semesterNumber
    );
}