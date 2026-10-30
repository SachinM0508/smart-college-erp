package com.college.erp.security;

import com.college.erp.entity.Role;
import com.college.erp.entity.RolePermission;
import com.college.erp.repository.RolePermissionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class PermissionChecker {

    private final RolePermissionRepository rolePermissionRepository;

    public PermissionChecker(RolePermissionRepository rolePermissionRepository) {
        this.rolePermissionRepository = rolePermissionRepository;
    }

    public boolean hasPermission(Authentication authentication, String permissionCode) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String authorityName = authority.getAuthority();

            if (authorityName.startsWith("ROLE_")) {

                String roleName = authorityName.substring(5);

                try {
                    Role role = Role.valueOf(roleName);

                    return rolePermissionRepository.findByRole(role)
                            .stream()
                            .map(RolePermission::getPermission)
                            .anyMatch(permission ->
                                    permission.getCode().equals(permissionCode)
                            );

                } catch (IllegalArgumentException e) {
                    return false;
                }
            }
        }

        return false;
    }
}