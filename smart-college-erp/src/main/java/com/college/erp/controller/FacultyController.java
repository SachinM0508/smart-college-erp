package com.college.erp.controller;

import com.college.erp.dto.FacultyCreateRequest;
import com.college.erp.dto.FacultyResponse;
import com.college.erp.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyResponse> createFaculty(
            @RequestBody FacultyCreateRequest request) {

        return ResponseEntity.ok(
                facultyService.createFaculty(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<FacultyResponse>> getAllFaculty() {

        return ResponseEntity.ok(
                facultyService.getAllFaculty()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyResponse> getFacultyById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                facultyService.getFacultyById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyResponse> updateFaculty(
            @PathVariable Long id,
            @RequestBody FacultyCreateRequest request) {

        return ResponseEntity.ok(
                facultyService.updateFaculty(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteFaculty(
            @PathVariable Long id) {

        facultyService.deleteFaculty(id);

        return ResponseEntity.ok(
                "Faculty deleted successfully"
        );
    }
}