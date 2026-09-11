package com.college.erp.controller;

import com.college.erp.dto.SemesterCreateRequest;
import com.college.erp.dto.SemesterResponse;
import com.college.erp.service.SemesterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/semesters")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    // Create semester
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<SemesterResponse> createSemester(
            @RequestBody SemesterCreateRequest request) {

        return ResponseEntity.ok(
                semesterService.createSemester(request)
        );
    }

    // Get all semesters
    @GetMapping
    public ResponseEntity<List<SemesterResponse>> getAllSemesters() {

        List<SemesterResponse> semesters =
                semesterService.getAllSemesters()
                        .stream()
                        .map(semesterService::convertToResponse)
                        .toList();

        return ResponseEntity.ok(semesters);
    }

    // Get semester by ID
    @GetMapping("/{id}")
    public ResponseEntity<SemesterResponse> getSemesterById(
            @PathVariable Long id) {

        return semesterService.getSemesterById(id)
                .map(semesterService::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update semester
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<SemesterResponse> updateSemester(
            @PathVariable Long id,
            @RequestBody SemesterCreateRequest request) {

        return ResponseEntity.ok(
                semesterService.updateSemester(id, request)
        );
    }

    // Delete semester
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSemester(
            @PathVariable Long id) {

        semesterService.deleteSemester(id);

        return ResponseEntity.noContent().build();
    }
}