package com.college.erp.controller;

import com.college.erp.dto.PermissionCreateRequest;
import com.college.erp.dto.PermissionResponse;
import com.college.erp.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(
            PermissionService permissionService) {

        this.permissionService = permissionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionResponse> createPermission(
            @RequestBody PermissionCreateRequest request) {

        return ResponseEntity.ok(
                permissionService.createPermission(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> getAllPermissions() {

        return ResponseEntity.ok(
                permissionService.getAllPermissions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> getPermissionById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                permissionService.getPermissionById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PermissionResponse> updatePermission(
            @PathVariable Long id,
            @RequestBody PermissionCreateRequest request) {

        return ResponseEntity.ok(
                permissionService.updatePermission(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePermission(
            @PathVariable Long id) {

        permissionService.deletePermission(id);

        return ResponseEntity.ok(
                "Permission deleted successfully"
        );
    }
}