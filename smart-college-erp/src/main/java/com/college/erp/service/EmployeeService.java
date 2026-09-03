package com.college.erp.service;

import com.college.erp.dto.EmployeeCreateRequest;
import org.springframework.transaction.annotation.Transactional;
import com.college.erp.exception.ResourceAlreadyExistsException;
import com.college.erp.entity.*;
import com.college.erp.repository.DepartmentRepository;
import com.college.erp.repository.DesignationRepository;
import com.college.erp.repository.EmployeeRepository;
import com.college.erp.repository.UserRepository;
import com.college.erp.dto.EmployeeResponse;
import org.springframework.stereotype.Service;
import com.college.erp.exception.ValidationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            DesignationRepository designationRepository,
            PasswordEncoder passwordEncoder) {

        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.designationRepository = designationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create employee + user account
    @Transactional
    public EmployeeResponse createEmployee(EmployeeCreateRequest request){

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException("Email already exists");
        }

        if (employeeRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new ResourceAlreadyExistsException("Employee ID already exists");
        }

        if (employeeRepository.existsByAadhar(request.getAadhar())) {
            throw new ResourceAlreadyExistsException("Aadhar already exists");
        }

        if (employeeRepository.existsByPanNumber(request.getPanNumber())) {
            throw new ResourceAlreadyExistsException("PAN number already exists");
        }

        // Check password confirmation
        if (request.getPassword() == null || request.getConfirmPassword() == null) {
            throw new ValidationException("Password and confirm password are required");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match");
        }

        // Find department
        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        // Find designation
        Designation designation = designationRepository
                .findById(request.getDesignationId())
                .orElseThrow(() ->
                        new RuntimeException("Designation not found"));

        // Create User
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYEE);

        User savedUser = userRepository.save(user);

        // Create Employee
        Employee employee = new Employee();

        employee.setEmployeeId(request.getEmployeeId());
        employee.setName(request.getName());
        employee.setPhone(request.getPhone());
        employee.setAddress(request.getAddress());
        employee.setDateOfBirth(request.getDateOfBirth());
        employee.setGender(request.getGender());
        employee.setBloodGroup(request.getBloodGroup());
        employee.setPhoto(request.getPhoto());
        employee.setAadhar(request.getAadhar());

        employee.setDepartment(department);
        employee.setDesignation(designation);
        employee.setEmployeeType(request.getEmployeeType());
        employee.setJoiningDate(request.getJoiningDate());
        employee.setStatus(request.getStatus());
        employee.setQualification(request.getQualification());
        employee.setExperience(request.getExperience());

        employee.setBasicSalary(request.getBasicSalary());
        employee.setPayGrade(request.getPayGrade());
        employee.setBankAccountNumber(request.getBankAccountNumber());
        employee.setIfscCode(request.getIfscCode());
        employee.setPanNumber(request.getPanNumber());
        employee.setSalaryMode(request.getSalaryMode());

        employee.setUser(savedUser);

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeResponse response = new EmployeeResponse();

        response.setId(savedEmployee.getId());
        response.setEmployeeId(savedEmployee.getEmployeeId());
        response.setName(savedEmployee.getName());
        response.setPhone(savedEmployee.getPhone());
        response.setAddress(savedEmployee.getAddress());
        response.setDateOfBirth(savedEmployee.getDateOfBirth());
        response.setGender(savedEmployee.getGender());
        response.setBloodGroup(savedEmployee.getBloodGroup());
        response.setPhoto(savedEmployee.getPhoto());
        response.setAadhar(savedEmployee.getAadhar());

        response.setDepartment(savedEmployee.getDepartment());
        response.setDesignation(savedEmployee.getDesignation());

        response.setEmployeeType(savedEmployee.getEmployeeType());
        response.setJoiningDate(savedEmployee.getJoiningDate());
        response.setStatus(savedEmployee.getStatus());
        response.setQualification(savedEmployee.getQualification());
        response.setExperience(savedEmployee.getExperience());

        response.setBasicSalary(savedEmployee.getBasicSalary());
        response.setPayGrade(savedEmployee.getPayGrade());
        response.setBankAccountNumber(savedEmployee.getBankAccountNumber());
        response.setIfscCode(savedEmployee.getIfscCode());
        response.setPanNumber(savedEmployee.getPanNumber());
        response.setSalaryMode(savedEmployee.getSalaryMode());

        response.setEmail(savedEmployee.getUser().getEmail());
        response.setRole(savedEmployee.getUser().getRole());

        return response;
    }

    public EmployeeResponse convertToResponse(Employee employee) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setEmployeeId(employee.getEmployeeId());
        response.setName(employee.getName());
        response.setPhone(employee.getPhone());
        response.setAddress(employee.getAddress());
        response.setDateOfBirth(employee.getDateOfBirth());
        response.setGender(employee.getGender());
        response.setBloodGroup(employee.getBloodGroup());
        response.setPhoto(employee.getPhoto());
        response.setAadhar(employee.getAadhar());

        response.setDepartment(employee.getDepartment());
        response.setDesignation(employee.getDesignation());

        response.setEmployeeType(employee.getEmployeeType());
        response.setJoiningDate(employee.getJoiningDate());
        response.setStatus(employee.getStatus());
        response.setQualification(employee.getQualification());
        response.setExperience(employee.getExperience());

        response.setBasicSalary(employee.getBasicSalary());
        response.setPayGrade(employee.getPayGrade());
        response.setBankAccountNumber(employee.getBankAccountNumber());
        response.setIfscCode(employee.getIfscCode());
        response.setPanNumber(employee.getPanNumber());
        response.setSalaryMode(employee.getSalaryMode());

        // Only send safe User information
        if (employee.getUser() != null) {
            response.setEmail(employee.getUser().getEmail());
            response.setRole(employee.getUser().getRole());
        }

        return response;
    }

    // Get all employees
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // Get employee by ID
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    // Delete employee
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}