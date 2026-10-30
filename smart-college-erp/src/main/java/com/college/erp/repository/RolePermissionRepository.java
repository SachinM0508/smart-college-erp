package com.college.erp.repository;

import com.college.erp.entity.Role;
import com.college.erp.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionRepository
        extends JpaRepository<RolePermission, Long> {

    List<RolePermission> findByRole(Role role);

    boolean existsByRoleAndPermissionId(
            Role role,
            Long permissionId
    );

    boolean existsByRoleAndPermissionIdAndIdNot(
            Role role,
            Long permissionId,
            Long id
    );
}