package com.college.erp.service;

import com.college.erp.dto.FacultyCreateRequest;
import com.college.erp.dto.FacultyResponse;
import com.college.erp.entity.Department;
import com.college.erp.entity.Designation;
import com.college.erp.entity.Faculty;
import com.college.erp.entity.Role;
import com.college.erp.entity.User;
import com.college.erp.repository.DepartmentRepository;
import com.college.erp.repository.DesignationRepository;
import com.college.erp.repository.FacultyRepository;
import com.college.erp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final DesignationRepository designationRepository;
    private final PasswordEncoder passwordEncoder;

    public FacultyService(
            FacultyRepository facultyRepository,
            UserRepository userRepository,
            DepartmentRepository departmentRepository,
            DesignationRepository designationRepository,
            PasswordEncoder passwordEncoder) {

        this.facultyRepository = facultyRepository;
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
        this.designationRepository = designationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // CREATE FACULTY
    // =========================

    @Transactional
    public FacultyResponse createFaculty(FacultyCreateRequest request) {

        if (facultyRepository.existsByFacultyId(request.getFacultyId())) {
            throw new RuntimeException("Faculty ID already exists");
        }

        if (request.getAadhar() != null &&
                facultyRepository.existsByAadhar(request.getAadhar())) {
            throw new RuntimeException("Aadhar already exists");
        }

        if (request.getEmployeeCode() != null &&
                facultyRepository.existsByEmployeeCode(request.getEmployeeCode())) {
            throw new RuntimeException("Employee code already exists");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        // Create User account
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.FACULTY);

        User savedUser = userRepository.save(user);

        // Create Faculty
        Faculty faculty = new Faculty();

        faculty.setFacultyId(request.getFacultyId());
        faculty.setName(request.getName());
        faculty.setPhone(request.getPhone());
        faculty.setAlternatePhone(request.getAlternatePhone());
        faculty.setAddress(request.getAddress());
        faculty.setCity(request.getCity());
        faculty.setState(request.getState());
        faculty.setPincode(request.getPincode());
        faculty.setDateOfBirth(request.getDateOfBirth());
        faculty.setGender(request.getGender());
        faculty.setBloodGroup(request.getBloodGroup());
        faculty.setAadhar(request.getAadhar());
        faculty.setPhoto(request.getPhoto());

        faculty.setEmployeeCode(request.getEmployeeCode());
        faculty.setDepartment(department);
        faculty.setDesignation(designation);
        faculty.setFacultyType(request.getFacultyType());
        faculty.setJoiningDate(request.getJoiningDate());
        faculty.setStatus(request.getStatus());
        faculty.setExperience(request.getExperience());

        faculty.setUser(savedUser);

        Faculty savedFaculty = facultyRepository.save(faculty);

        return convertToResponse(savedFaculty);
    }

    // =========================
    // GET ALL FACULTY
    // =========================

    public List<FacultyResponse> getAllFaculty() {

        return facultyRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // =========================
    // GET FACULTY BY ID
    // =========================

    public FacultyResponse getFacultyById(Long id) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        return convertToResponse(faculty);
    }

    // =========================
    // UPDATE FACULTY
    // =========================

    @Transactional
    public FacultyResponse updateFaculty(
            Long id,
            FacultyCreateRequest request) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        if (facultyRepository.existsByFacultyIdAndIdNot(
                request.getFacultyId(), id)) {

            throw new RuntimeException("Faculty ID already exists");
        }

        if (request.getAadhar() != null &&
                facultyRepository.existsByAadharAndIdNot(
                        request.getAadhar(), id)) {

            throw new RuntimeException("Aadhar already exists");
        }

        if (request.getEmployeeCode() != null &&
                facultyRepository.existsByEmployeeCodeAndIdNot(
                        request.getEmployeeCode(), id)) {

            throw new RuntimeException("Employee code already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Designation designation = designationRepository.findById(request.getDesignationId())
                .orElseThrow(() -> new RuntimeException("Designation not found"));

        faculty.setFacultyId(request.getFacultyId());
        faculty.setName(request.getName());
        faculty.setPhone(request.getPhone());
        faculty.setAlternatePhone(request.getAlternatePhone());
        faculty.setAddress(request.getAddress());
        faculty.setCity(request.getCity());
        faculty.setState(request.getState());
        faculty.setPincode(request.getPincode());
        faculty.setDateOfBirth(request.getDateOfBirth());
        faculty.setGender(request.getGender());
        faculty.setBloodGroup(request.getBloodGroup());
        faculty.setAadhar(request.getAadhar());
        faculty.setPhoto(request.getPhoto());

        faculty.setEmployeeCode(request.getEmployeeCode());
        faculty.setDepartment(department);
        faculty.setDesignation(designation);
        faculty.setFacultyType(request.getFacultyType());
        faculty.setJoiningDate(request.getJoiningDate());
        faculty.setStatus(request.getStatus());
        faculty.setExperience(request.getExperience());

        Faculty updatedFaculty = facultyRepository.save(faculty);

        return convertToResponse(updatedFaculty);
    }

    // =========================
    // DELETE FACULTY
    // =========================

    @Transactional
    public void deleteFaculty(Long id) {

        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        User user = faculty.getUser();

        facultyRepository.delete(faculty);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    // =========================
    // CONVERT TO RESPONSE
    // =========================

    private FacultyResponse convertToResponse(Faculty faculty) {

        FacultyResponse response = new FacultyResponse();

        response.setId(faculty.getId());

        response.setFacultyId(faculty.getFacultyId());
        response.setName(faculty.getName());
        response.setPhone(faculty.getPhone());
        response.setAlternatePhone(faculty.getAlternatePhone());
        response.setAddress(faculty.getAddress());
        response.setCity(faculty.getCity());
        response.setState(faculty.getState());
        response.setPincode(faculty.getPincode());
        response.setDateOfBirth(faculty.getDateOfBirth());
        response.setGender(faculty.getGender());
        response.setBloodGroup(faculty.getBloodGroup());
        response.setAadhar(faculty.getAadhar());
        response.setPhoto(faculty.getPhoto());

        response.setEmployeeCode(faculty.getEmployeeCode());

        if (faculty.getDepartment() != null) {
            response.setDepartmentId(
                    faculty.getDepartment().getId()
            );

            response.setDepartmentName(
                    faculty.getDepartment().getName()
            );
        }

        if (faculty.getDesignation() != null) {
            response.setDesignationId(
                    faculty.getDesignation().getId()
            );

            response.setDesignationName(
                    faculty.getDesignation().getName()
            );
        }

        response.setFacultyType(faculty.getFacultyType());
        response.setJoiningDate(faculty.getJoiningDate());
        response.setStatus(faculty.getStatus());
        response.setExperience(faculty.getExperience());

        if (faculty.getUser() != null) {
            response.setUserId(faculty.getUser().getId());
            response.setEmail(faculty.getUser().getEmail());
            response.setRole(
                    faculty.getUser().getRole().name()
            );
        }

        return response;
    }
}