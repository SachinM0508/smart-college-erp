package com.college.erp.dto;

import com.college.erp.entity.Role;

public class RolePermissionCreateRequest {

    private Role role;

    private Long permissionId;

    public RolePermissionCreateRequest() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}