package com.college.erp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class PermissionTestController {

    @GetMapping("/attendance-view")
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_VIEW')")
    public String testAttendanceViewPermission() {

        return "ATTENDANCE_VIEW permission granted successfully.";
    }
}