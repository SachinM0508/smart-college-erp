package com.college.erp.repository;

import com.college.erp.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByEmployeeId(String employeeId);

    boolean existsByAadhar(String aadhar);

    boolean existsByPanNumber(String panNumber);
}