package com.college.erp.controller;

import com.college.erp.dto.StudentEnrollmentCreateRequest;
import com.college.erp.dto.StudentEnrollmentResponse;
import com.college.erp.service.StudentEnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-enrollments")
public class StudentEnrollmentController {

    private final StudentEnrollmentService studentEnrollmentService;

    public StudentEnrollmentController(
            StudentEnrollmentService studentEnrollmentService) {
        this.studentEnrollmentService = studentEnrollmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentEnrollmentResponse> createEnrollment(
            @RequestBody StudentEnrollmentCreateRequest request) {

        return ResponseEntity.ok(
                studentEnrollmentService.createEnrollment(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<StudentEnrollmentResponse>> getAllEnrollments() {

        return ResponseEntity.ok(
                studentEnrollmentService.getAllEnrollments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEnrollmentResponse> getEnrollmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                studentEnrollmentService.getEnrollmentById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentEnrollmentResponse> updateEnrollment(
            @PathVariable Long id,
            @RequestBody StudentEnrollmentCreateRequest request) {

        return ResponseEntity.ok(
                studentEnrollmentService.updateEnrollment(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteEnrollment(
            @PathVariable Long id) {

        studentEnrollmentService.deleteEnrollment(id);

        return ResponseEntity.ok(
                "Student enrollment deleted successfully"
        );
    }
}