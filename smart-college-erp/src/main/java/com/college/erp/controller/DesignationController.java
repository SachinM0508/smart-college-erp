package com.college.erp.controller;

import com.college.erp.entity.Designation;
import com.college.erp.service.DesignationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designations")
public class DesignationController {

    private final DesignationService designationService;

    public DesignationController(DesignationService designationService) {
        this.designationService = designationService;
    }

    // Create designation
    @PostMapping
    public ResponseEntity<Designation> createDesignation(
            @RequestBody Designation designation) {

        Designation savedDesignation =
                designationService.createDesignation(designation);

        return ResponseEntity.ok(savedDesignation);
    }

    // Get all designations
    @GetMapping
    public ResponseEntity<List<Designation>> getAllDesignations() {

        return ResponseEntity.ok(
                designationService.getAllDesignations()
        );
    }

    // Get designation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Designation> getDesignationById(
            @PathVariable Long id) {

        return designationService.getDesignationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete designation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesignation(
            @PathVariable Long id) {

        designationService.deleteDesignation(id);

        return ResponseEntity.noContent().build();
    }
}