package com.college.erp.controller;

import com.college.erp.dto.SectionCreateRequest;
import com.college.erp.dto.SectionResponse;
import com.college.erp.service.SectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SectionResponse> createSection(
            @RequestBody SectionCreateRequest request) {

        return ResponseEntity.ok(
                sectionService.createSection(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<SectionResponse>> getAllSections() {

        return ResponseEntity.ok(
                sectionService.getAllSections()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionResponse> getSectionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                sectionService.getSectionById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SectionResponse> updateSection(
            @PathVariable Long id,
            @RequestBody SectionCreateRequest request) {

        return ResponseEntity.ok(
                sectionService.updateSection(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteSection(
            @PathVariable Long id) {

        sectionService.deleteSection(id);

        return ResponseEntity.ok(
                "Section deleted successfully"
        );
    }
}