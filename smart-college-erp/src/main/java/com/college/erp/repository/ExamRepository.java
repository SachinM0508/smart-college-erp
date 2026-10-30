package com.college.erp.repository;

import com.college.erp.entity.Exam;
import com.college.erp.entity.ExamType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByAcademicYearId(Long academicYearId);

    List<Exam> findByCourseId(Long courseId);

    List<Exam> findBySemesterId(Long semesterId);

    List<Exam> findByStudentGroupId(Long studentGroupId);

    List<Exam> findByExamType(ExamType examType);

    boolean existsByExamNameAndStudentGroupIdAndAcademicYearId(
            String examName,
            Long studentGroupId,
            Long academicYearId
    );

    boolean existsByExamNameAndStudentGroupIdAndAcademicYearIdAndIdNot(
            String examName,
            Long studentGroupId,
            Long academicYearId,
            Long id
    );
}