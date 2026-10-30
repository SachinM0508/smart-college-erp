package com.college.erp.service;

import com.college.erp.dto.PermissionCreateRequest;
import com.college.erp.dto.PermissionResponse;
import com.college.erp.entity.Permission;
import com.college.erp.repository.PermissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public PermissionResponse createPermission(
            PermissionCreateRequest request) {

        if (permissionRepository.existsByCode(request.getCode())) {
            throw new RuntimeException(
                    "Permission code already exists");
        }

        Permission permission = new Permission();

        permission.setCode(request.getCode());
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());

        Permission savedPermission =
                permissionRepository.save(permission);

        return convertToResponse(savedPermission);
    }

    public List<PermissionResponse> getAllPermissions() {

        return permissionRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public PermissionResponse getPermissionById(Long id) {

        Permission permission =
                permissionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Permission not found"));

        return convertToResponse(permission);
    }

    public PermissionResponse updatePermission(
            Long id,
            PermissionCreateRequest request) {

        Permission permission =
                permissionRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Permission not found"));

        permissionRepository.findByCode(request.getCode())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new RuntimeException(
                                "Permission code already exists");
                    }
                });

        permission.setCode(request.getCode());
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());

        Permission updatedPermission =
                permissionRepository.save(permission);

        return convertToResponse(updatedPermission);
    }

    public void deletePermission(Long id) {

        if (!permissionRepository.existsById(id)) {
            throw new RuntimeException(
                    "Permission not found");
        }

        permissionRepository.deleteById(id);
    }

    private PermissionResponse convertToResponse(
            Permission permission) {

        PermissionResponse response =
                new PermissionResponse();

        response.setId(permission.getId());
        response.setCode(permission.getCode());
        response.setName(permission.getName());
        response.setDescription(permission.getDescription());

        return response;
    }
}