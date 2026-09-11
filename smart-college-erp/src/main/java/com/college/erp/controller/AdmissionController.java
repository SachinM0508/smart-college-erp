package com.college.erp.controller;

import com.college.erp.dto.AdmissionCreateRequest;
import com.college.erp.dto.AdmissionResponse;
import com.college.erp.service.AdmissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    private final AdmissionService admissionService;

    public AdmissionController(AdmissionService admissionService) {
        this.admissionService = admissionService;
    }

    // Create admission
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AdmissionResponse> createAdmission(
            @RequestBody AdmissionCreateRequest request) {

        AdmissionResponse savedAdmission =
                admissionService.createAdmission(request);

        return ResponseEntity.ok(savedAdmission);
    }

    // Get all admissions
    @GetMapping
    public ResponseEntity<List<AdmissionResponse>> getAllAdmissions() {

        List<AdmissionResponse> admissions =
                admissionService.getAllAdmissions()
                        .stream()
                        .map(admissionService::convertToResponse)
                        .toList();

        return ResponseEntity.ok(admissions);
    }

    // Get admission by ID
    @GetMapping("/{id}")
    public ResponseEntity<AdmissionResponse> getAdmissionById(
            @PathVariable Long id) {

        return admissionService.getAdmissionById(id)
                .map(admissionService::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Get admission by student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<AdmissionResponse> getAdmissionByStudentId(
            @PathVariable Long studentId) {

        return admissionService.getAdmissionByStudentId(studentId)
                .map(admissionService::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update admission
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AdmissionResponse> updateAdmission(
            @PathVariable Long id,
            @RequestBody AdmissionCreateRequest request) {

        AdmissionResponse updatedAdmission =
                admissionService.updateAdmission(id, request);

        return ResponseEntity.ok(updatedAdmission);
    }

    // Delete admission
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmission(
            @PathVariable Long id) {

        admissionService.deleteAdmission(id);

        return ResponseEntity.noContent().build();
    }
}