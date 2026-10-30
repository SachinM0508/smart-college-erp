package com.college.erp.controller;

import com.college.erp.dto.ExamCreateRequest;
import com.college.erp.dto.ExamResponse;
import com.college.erp.entity.ExamType;
import com.college.erp.service.ExamService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExamResponse> createExam(
            @RequestBody ExamCreateRequest request) {

        return ResponseEntity.ok(
                examService.createExam(request)
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExamResponse>> getAllExams() {

        return ResponseEntity.ok(
                examService.getAllExams()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExamResponse> getExamById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                examService.getExamById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExamResponse> updateExam(
            @PathVariable Long id,
            @RequestBody ExamCreateRequest request) {

        return ResponseEntity.ok(
                examService.updateExam(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExam(
            @PathVariable Long id) {

        examService.deleteExam(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/academic-year/{academicYearId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExamResponse>> getByAcademicYear(
            @PathVariable Long academicYearId) {

        return ResponseEntity.ok(
                examService.getExamsByAcademicYear(academicYearId)
        );
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExamResponse>> getByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                examService.getExamsByCourse(courseId)
        );
    }

    @GetMapping("/semester/{semesterId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExamResponse>> getBySemester(
            @PathVariable Long semesterId) {

        return ResponseEntity.ok(
                examService.getExamsBySemester(semesterId)
        );
    }

    @GetMapping("/group/{studentGroupId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExamResponse>> getByStudentGroup(
            @PathVariable Long studentGroupId) {

        return ResponseEntity.ok(
                examService.getExamsByStudentGroup(studentGroupId)
        );
    }

    @GetMapping("/type/{examType}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExamResponse>> getByExamType(
            @PathVariable ExamType examType) {

        return ResponseEntity.ok(
                examService.getExamsByExamType(examType)
        );
    }
}