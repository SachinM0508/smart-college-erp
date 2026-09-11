package com.college.erp.controller;

import com.college.erp.dto.AcademicYearCreateRequest;
import com.college.erp.dto.AcademicYearResponse;
import com.college.erp.service.AcademicYearService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/academic-years")
public class AcademicYearController {

    private final AcademicYearService academicYearService;

    public AcademicYearController(
            AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    // Create academic year
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AcademicYearResponse> createAcademicYear(
            @RequestBody AcademicYearCreateRequest request) {

        return ResponseEntity.ok(
                academicYearService.createAcademicYear(request)
        );
    }

    // Get all academic years
    @GetMapping
    public ResponseEntity<List<AcademicYearResponse>> getAllAcademicYears() {

        List<AcademicYearResponse> academicYears =
                academicYearService.getAllAcademicYears()
                        .stream()
                        .map(academicYearService::convertToResponse)
                        .toList();

        return ResponseEntity.ok(academicYears);
    }

    // Get academic year by ID
    @GetMapping("/{id}")
    public ResponseEntity<AcademicYearResponse> getAcademicYearById(
            @PathVariable Long id) {

        return academicYearService.getAcademicYearById(id)
                .map(academicYearService::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update academic year
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AcademicYearResponse> updateAcademicYear(
            @PathVariable Long id,
            @RequestBody AcademicYearCreateRequest request) {

        return ResponseEntity.ok(
                academicYearService.updateAcademicYear(id, request)
        );
    }

    // Delete academic year
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicYear(
            @PathVariable Long id) {

        academicYearService.deleteAcademicYear(id);

        return ResponseEntity.noContent().build();
    }
}