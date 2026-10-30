package com.college.erp.controller;

import com.college.erp.dto.FacultyPayrollCreateRequest;
import com.college.erp.dto.FacultyPayrollResponse;
import com.college.erp.service.FacultyPayrollService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty-payroll")
public class FacultyPayrollController {

    private final FacultyPayrollService payrollService;

    public FacultyPayrollController(
            FacultyPayrollService payrollService) {

        this.payrollService = payrollService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyPayrollResponse> createPayroll(
            @RequestBody FacultyPayrollCreateRequest request) {

        return ResponseEntity.ok(
                payrollService.createPayroll(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<FacultyPayrollResponse>> getAllPayrolls() {

        return ResponseEntity.ok(
                payrollService.getAllPayrolls()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<FacultyPayrollResponse> getPayrollById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                payrollService.getPayrollById(id)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FacultyPayrollResponse> updatePayroll(
            @PathVariable Long id,
            @RequestBody FacultyPayrollCreateRequest request) {

        return ResponseEntity.ok(
                payrollService.updatePayroll(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deletePayroll(
            @PathVariable Long id) {

        payrollService.deletePayroll(id);

        return ResponseEntity.ok(
                "Faculty payroll deleted successfully"
        );
    }
}