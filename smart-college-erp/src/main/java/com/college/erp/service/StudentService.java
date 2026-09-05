package com.college.erp.service;

import com.college.erp.dto.StudentCreateRequest;
import com.college.erp.dto.StudentResponse;
import com.college.erp.entity.Role;
import com.college.erp.entity.Student;
import com.college.erp.entity.User;
import com.college.erp.repository.StudentRepository;
import com.college.erp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentService(
            StudentRepository studentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create student
    public StudentResponse createStudent(StudentCreateRequest request) {

        // Check Student ID
        if (studentRepository.existsByStudentId(request.getStudentId())) {
            throw new RuntimeException("Student ID already exists");
        }

        // Check Aadhar
        if (request.getAadhar() != null &&
                studentRepository.existsByAadhar(request.getAadhar())) {
            throw new RuntimeException("Aadhar already exists");
        }

        // Check APAAR ID
        if (request.getApaarId() != null &&
                studentRepository.existsByApaarId(request.getApaarId())) {
            throw new RuntimeException("APAAR ID already exists");
        }

        // Check email
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        // Password confirmation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }

        // Create User
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.STUDENT);

        User savedUser = userRepository.save(user);

        // Create Student
        Student student = new Student();

        student.setStudentId(request.getStudentId());
        student.setApaarId(request.getApaarId());
        student.setAadhar(request.getAadhar());

        student.setName(request.getName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setBloodGroup(request.getBloodGroup());
        student.setPhoto(request.getPhoto());

        student.setPhone(request.getPhone());
        student.setAlternatePhone(request.getAlternatePhone());
        student.setAddress(request.getAddress());
        student.setCity(request.getCity());
        student.setState(request.getState());
        student.setPincode(request.getPincode());

        student.setFatherName(request.getFatherName());
        student.setMotherName(request.getMotherName());
        student.setGuardianName(request.getGuardianName());
        student.setGuardianPhone(request.getGuardianPhone());
        student.setGuardianOccupation(request.getGuardianOccupation());

        student.setUser(savedUser);

        Student savedStudent = studentRepository.save(student);

        return convertToResponse(savedStudent);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get student by ID
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    // Update student
    public StudentResponse updateStudent(
            Long id,
            StudentCreateRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        // Check Student ID if it is being changed
        if (!student.getStudentId().equals(request.getStudentId())
                && studentRepository.existsByStudentId(request.getStudentId())) {
            throw new RuntimeException("Student ID already exists");
        }

        // Check Aadhar if it is being changed
        if (request.getAadhar() != null
                && !request.getAadhar().equals(student.getAadhar())
                && studentRepository.existsByAadhar(request.getAadhar())) {
            throw new RuntimeException("Aadhar already exists");
        }

        // Check APAAR ID if it is being changed
        if (request.getApaarId() != null
                && !request.getApaarId().equals(student.getApaarId())
                && studentRepository.existsByApaarId(request.getApaarId())) {
            throw new RuntimeException("APAAR ID already exists");
        }

        // Update student fields
        student.setStudentId(request.getStudentId());
        student.setApaarId(request.getApaarId());
        student.setAadhar(request.getAadhar());

        student.setName(request.getName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setBloodGroup(request.getBloodGroup());
        student.setPhoto(request.getPhoto());

        student.setPhone(request.getPhone());
        student.setAlternatePhone(request.getAlternatePhone());
        student.setAddress(request.getAddress());
        student.setCity(request.getCity());
        student.setState(request.getState());
        student.setPincode(request.getPincode());

        student.setFatherName(request.getFatherName());
        student.setMotherName(request.getMotherName());
        student.setGuardianName(request.getGuardianName());
        student.setGuardianPhone(request.getGuardianPhone());
        student.setGuardianOccupation(request.getGuardianOccupation());

        // Update login email/password
        User user = student.getUser();

        if (user != null) {

            if (!user.getEmail().equals(request.getEmail())
                    && userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new RuntimeException("Email already exists");
            }

            user.setEmail(request.getEmail());

            if (request.getPassword() != null
                    && !request.getPassword().isBlank()) {

                if (!request.getPassword()
                        .equals(request.getConfirmPassword())) {
                    throw new RuntimeException("Passwords do not match");
                }

                user.setPassword(
                        passwordEncoder.encode(request.getPassword())
                );
            }

            userRepository.save(user);
        }

        Student updatedStudent = studentRepository.save(student);

        return convertToResponse(updatedStudent);
    }

    // Delete student
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        User user = student.getUser();

        studentRepository.delete(student);

        if (user != null) {
            userRepository.delete(user);
        }
    }

    // Convert Entity → Response
    public StudentResponse convertToResponse(Student student) {

        StudentResponse response = new StudentResponse();

        response.setId(student.getId());
        response.setStudentId(student.getStudentId());
        response.setApaarId(student.getApaarId());
        response.setAadhar(student.getAadhar());

        response.setName(student.getName());
        response.setDateOfBirth(student.getDateOfBirth());
        response.setGender(student.getGender());
        response.setBloodGroup(student.getBloodGroup());
        response.setPhoto(student.getPhoto());

        response.setPhone(student.getPhone());
        response.setAlternatePhone(student.getAlternatePhone());
        response.setAddress(student.getAddress());
        response.setCity(student.getCity());
        response.setState(student.getState());
        response.setPincode(student.getPincode());

        response.setFatherName(student.getFatherName());
        response.setMotherName(student.getMotherName());
        response.setGuardianName(student.getGuardianName());
        response.setGuardianPhone(student.getGuardianPhone());
        response.setGuardianOccupation(student.getGuardianOccupation());

        if (student.getUser() != null) {
            response.setEmail(student.getUser().getEmail());
            response.setRole(student.getUser().getRole().name());
        }

        return response;
    }
}