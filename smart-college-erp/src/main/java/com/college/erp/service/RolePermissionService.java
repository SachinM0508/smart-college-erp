package com.college.erp.service;

import com.college.erp.dto.RolePermissionCreateRequest;
import com.college.erp.dto.RolePermissionResponse;
import com.college.erp.entity.Permission;
import com.college.erp.entity.RolePermission;
import com.college.erp.repository.PermissionRepository;
import com.college.erp.repository.RolePermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository permissionRepository;

    public RolePermissionService(
            RolePermissionRepository rolePermissionRepository,
            PermissionRepository permissionRepository) {

        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository = permissionRepository;
    }

    public RolePermissionResponse createRolePermission(
            RolePermissionCreateRequest request) {

        Permission permission = permissionRepository
                .findById(request.getPermissionId())
                .orElseThrow(() ->
                        new RuntimeException("Permission not found"));

        if (rolePermissionRepository
                .existsByRoleAndPermissionId(
                        request.getRole(),
                        request.getPermissionId())) {

            throw new RuntimeException(
                    "Permission already assigned to this role");
        }

        RolePermission rolePermission = new RolePermission();

        rolePermission.setRole(request.getRole());
        rolePermission.setPermission(permission);

        RolePermission savedRolePermission =
                rolePermissionRepository.save(rolePermission);

        return convertToResponse(savedRolePermission);
    }

    public List<RolePermissionResponse> getAllRolePermissions() {

        return rolePermissionRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public RolePermissionResponse getRolePermissionById(Long id) {

        RolePermission rolePermission =
                rolePermissionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role permission not found"));

        return convertToResponse(rolePermission);
    }

    public List<RolePermissionResponse> getPermissionsByRole(
            com.college.erp.entity.Role role) {

        return rolePermissionRepository.findByRole(role)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public RolePermissionResponse updateRolePermission(
            Long id,
            RolePermissionCreateRequest request) {

        RolePermission rolePermission =
                rolePermissionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Role permission not found"));

        Permission permission = permissionRepository
                .findById(request.getPermissionId())
                .orElseThrow(() ->
                        new RuntimeException("Permission not found"));

        if (rolePermissionRepository
                .existsByRoleAndPermissionIdAndIdNot(
                        request.getRole(),
                        request.getPermissionId(),
                        id)) {

            throw new RuntimeException(
                    "Permission already assigned to this role");
        }

        rolePermission.setRole(request.getRole());
        rolePermission.setPermission(permission);

        RolePermission updatedRolePermission =
                rolePermissionRepository.save(rolePermission);

        return convertToResponse(updatedRolePermission);
    }

    public void deleteRolePermission(Long id) {

        if (!rolePermissionRepository.existsById(id)) {
            throw new RuntimeException(
                    "Role permission not found");
        }

        rolePermissionRepository.deleteById(id);
    }

    private RolePermissionResponse convertToResponse(
            RolePermission rolePermission) {

        RolePermissionResponse response =
                new RolePermissionResponse();

        Permission permission =
                rolePermission.getPermission();

        response.setId(rolePermission.getId());
        response.setRole(rolePermission.getRole());

        response.setPermissionId(permission.getId());
        response.setPermissionCode(permission.getCode());
        response.setPermissionName(permission.getName());
        response.setPermissionDescription(
                permission.getDescription());

        return response;
    }
}