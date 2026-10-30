package com.college.erp.controller;

import com.college.erp.dto.RolePermissionCreateRequest;
import com.college.erp.dto.RolePermissionResponse;
import com.college.erp.entity.Role;
import com.college.erp.service.RolePermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-permissions")
public class RolePermissionController {

    private final RolePermissionService rolePermissionService;

    public RolePermissionController(
            RolePermissionService rolePermissionService) {

        this.rolePermissionService = rolePermissionService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolePermissionResponse> createRolePermission(
            @RequestBody RolePermissionCreateRequest request) {

        return ResponseEntity.ok(
                rolePermissionService.createRolePermission(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<RolePermissionResponse>>
    getAllRolePermissions() {

        return ResponseEntity.ok(
                rolePermissionService.getAllRolePermissions()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolePermissionResponse>
    getRolePermissionById(@PathVariable Long id) {

        return ResponseEntity.ok(
                rolePermissionService.getRolePermissionById(id)
        );
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<RolePermissionResponse>>
    getPermissionsByRole(@PathVariable Role role) {

        return ResponseEntity.ok(
                rolePermissionService.getPermissionsByRole(role)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RolePermissionResponse>
    updateRolePermission(
            @PathVariable Long id,
            @RequestBody RolePermissionCreateRequest request) {

        return ResponseEntity.ok(
                rolePermissionService.updateRolePermission(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteRolePermission(
            @PathVariable Long id) {

        rolePermissionService.deleteRolePermission(id);

        return ResponseEntity.ok(
                "Role permission deleted successfully"
        );
    }
}