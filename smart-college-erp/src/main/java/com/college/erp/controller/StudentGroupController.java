package com.college.erp.controller;

import com.college.erp.dto.StudentGroupCreateRequest;
import com.college.erp.dto.StudentGroupResponse;
import com.college.erp.service.StudentGroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student-groups")
public class StudentGroupController {

    private final StudentGroupService studentGroupService;

    public StudentGroupController(StudentGroupService studentGroupService) {
        this.studentGroupService = studentGroupService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentGroupResponse> createStudentGroup(
            @RequestBody StudentGroupCreateRequest request) {

        return ResponseEntity.ok(
                studentGroupService.createStudentGroup(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<StudentGroupResponse>> getAllStudentGroups() {

        return ResponseEntity.ok(
                studentGroupService.getAllStudentGroups()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentGroupResponse> getStudentGroupById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                studentGroupService.getStudentGroupById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentGroupResponse> updateStudentGroup(
            @PathVariable Long id,
            @RequestBody StudentGroupCreateRequest request) {

        return ResponseEntity.ok(
                studentGroupService.updateStudentGroup(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStudentGroup(
            @PathVariable Long id) {

        studentGroupService.deleteStudentGroup(id);

        return ResponseEntity.ok("Student group deleted successfully");
    }
}