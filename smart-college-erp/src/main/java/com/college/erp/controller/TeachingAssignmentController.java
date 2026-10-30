package com.college.erp.controller;

import com.college.erp.dto.TeachingAssignmentCreateRequest;
import com.college.erp.dto.TeachingAssignmentResponse;
import com.college.erp.service.TeachingAssignmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teaching-assignments")
public class TeachingAssignmentController {

    private final TeachingAssignmentService assignmentService;

    public TeachingAssignmentController(
            TeachingAssignmentService assignmentService) {

        this.assignmentService = assignmentService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeachingAssignmentResponse> createAssignment(
            @RequestBody TeachingAssignmentCreateRequest request) {

        return ResponseEntity.ok(
                assignmentService.createAssignment(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<TeachingAssignmentResponse>>
    getAllAssignments() {

        return ResponseEntity.ok(
                assignmentService.getAllAssignments()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeachingAssignmentResponse>
    getAssignmentById(@PathVariable Long id) {

        return ResponseEntity.ok(
                assignmentService.getAssignmentById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TeachingAssignmentResponse>
    updateAssignment(
            @PathVariable Long id,
            @RequestBody TeachingAssignmentCreateRequest request) {

        return ResponseEntity.ok(
                assignmentService.updateAssignment(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAssignment(
            @PathVariable Long id) {

        assignmentService.deleteAssignment(id);

        return ResponseEntity.ok(
                "Teaching assignment deleted successfully"
        );
    }
}