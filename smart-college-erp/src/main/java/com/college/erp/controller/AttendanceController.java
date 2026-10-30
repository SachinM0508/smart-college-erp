package com.college.erp.controller;

import com.college.erp.dto.AttendanceCreateRequest;
import com.college.erp.dto.AttendanceResponse;
import com.college.erp.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_MARK')")
    public ResponseEntity<AttendanceResponse> createAttendance(
            @RequestBody AttendanceCreateRequest request) {

        AttendanceResponse response =
                attendanceService.createAttendance(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_MARK')")
    public ResponseEntity<AttendanceResponse> updateAttendance(
            @PathVariable Long id,
            @RequestBody AttendanceCreateRequest request) {

        AttendanceResponse response =
                attendanceService.updateAttendance(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_MARK')")
    public ResponseEntity<Void> deleteAttendance(
            @PathVariable Long id) {

        attendanceService.deleteAttendance(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_VIEW')")
    public ResponseEntity<List<AttendanceResponse>> getAllAttendance() {

        return ResponseEntity.ok(
                attendanceService.getAllAttendance()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_VIEW')")
    public ResponseEntity<AttendanceResponse> getAttendanceById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceById(id)
        );
    }

    @GetMapping("/student/{studentId}")
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_VIEW')")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByStudent(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByStudent(studentId)
        );
    }

    @GetMapping("/subject/{subjectId}")
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_VIEW')")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceBySubject(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceBySubject(subjectId)
        );
    }

    @GetMapping("/group/{studentGroupId}")
    @PreAuthorize("@permissionChecker.hasPermission(authentication, 'ATTENDANCE_VIEW')")
    public ResponseEntity<List<AttendanceResponse>> getAttendanceByGroup(
            @PathVariable Long studentGroupId) {

        return ResponseEntity.ok(
                attendanceService.getAttendanceByGroup(studentGroupId)
        );
    }
}