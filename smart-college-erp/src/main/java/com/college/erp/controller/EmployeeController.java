package com.college.erp.controller;

import com.college.erp.entity.Employee;
import com.college.erp.service.EmployeeService;
import com.college.erp.dto.EmployeeCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.college.erp.dto.EmployeeResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // Create employee
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmployeeResponse> createEmployee(
            @RequestBody EmployeeCreateRequest request) {

        EmployeeResponse savedEmployee = employeeService.createEmployee(request);

        return ResponseEntity.ok(savedEmployee);
    }

    // Get all employees
    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
        List<EmployeeResponse> employees = employeeService.getAllEmployees()
                .stream()
                .map(employeeService::convertToResponse)
                .toList();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id)
                .map(employeeService::convertToResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update employee
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeCreateRequest request) {

        EmployeeResponse updatedEmployee =
                employeeService.updateEmployee(id, request);

        return ResponseEntity.ok(updatedEmployee);
    }

    // Delete employee
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }
}