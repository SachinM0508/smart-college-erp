package com.college.erp.controller;

import com.college.erp.dto.FacultyQualificationCreateRequest;
import com.college.erp.dto.FacultyQualificationResponse;
import com.college.erp.service.FacultyQualificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty-qualifications")
public class FacultyQualificationController {

    private final FacultyQualificationService qualificationService;

    public FacultyQualificationController(
            FacultyQualificationService qualificationService) {

        this.qualificationService = qualificationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyQualificationResponse> createQualification(
            @RequestBody FacultyQualificationCreateRequest request) {

        return ResponseEntity.ok(
                qualificationService.createQualification(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<FacultyQualificationResponse>>
    getAllQualifications() {

        return ResponseEntity.ok(
                qualificationService.getAllQualifications()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyQualificationResponse>
    getQualificationById(@PathVariable Long id) {

        return ResponseEntity.ok(
                qualificationService.getQualificationById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyQualificationResponse>
    updateQualification(
            @PathVariable Long id,
            @RequestBody FacultyQualificationCreateRequest request) {

        return ResponseEntity.ok(
                qualificationService.updateQualification(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteQualification(
            @PathVariable Long id) {

        qualificationService.deleteQualification(id);

        return ResponseEntity.ok(
                "Faculty qualification deleted successfully"
        );
    }
}