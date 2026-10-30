package com.college.erp.controller;

import com.college.erp.dto.SubjectCreateRequest;
import com.college.erp.dto.SubjectResponse;
import com.college.erp.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponse> createSubject(
            @RequestBody SubjectCreateRequest request) {

        return ResponseEntity.ok(
                subjectService.createSubject(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {

        return ResponseEntity.ok(
                subjectService.getAllSubjects()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                subjectService.getSubjectById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubjectResponse> updateSubject(
            @PathVariable Long id,
            @RequestBody SubjectCreateRequest request) {

        return ResponseEntity.ok(
                subjectService.updateSubject(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSubject(
            @PathVariable Long id) {

        subjectService.deleteSubject(id);

        return ResponseEntity.ok(
                "Subject deleted successfully"
        );
    }
}