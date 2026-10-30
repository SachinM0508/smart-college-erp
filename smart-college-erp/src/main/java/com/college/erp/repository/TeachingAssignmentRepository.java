package com.college.erp.repository;

import com.college.erp.entity.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachingAssignmentRepository
        extends JpaRepository<TeachingAssignment, Long> {

    boolean existsByFacultyIdAndSubjectIdAndStudentGroupIdAndAcademicYearId(
            Long facultyId,
            Long subjectId,
            Long studentGroupId,
            Long academicYearId
    );

    boolean existsByFacultyIdAndSubjectIdAndStudentGroupIdAndAcademicYearIdAndIdNot(
            Long facultyId,
            Long subjectId,
            Long studentGroupId,
            Long academicYearId,
            Long id
    );
}