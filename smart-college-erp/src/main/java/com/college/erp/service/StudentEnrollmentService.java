package com.college.erp.service;

import com.college.erp.dto.StudentEnrollmentCreateRequest;
import com.college.erp.dto.StudentEnrollmentResponse;
import com.college.erp.entity.AcademicYear;
import com.college.erp.entity.Student;
import com.college.erp.entity.StudentEnrollment;
import com.college.erp.entity.StudentGroup;
import com.college.erp.repository.AcademicYearRepository;
import com.college.erp.repository.StudentEnrollmentRepository;
import com.college.erp.repository.StudentGroupRepository;
import com.college.erp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentRepository studentRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final AcademicYearRepository academicYearRepository;

    public StudentEnrollmentService(
            StudentEnrollmentRepository studentEnrollmentRepository,
            StudentRepository studentRepository,
            StudentGroupRepository studentGroupRepository,
            AcademicYearRepository academicYearRepository) {

        this.studentEnrollmentRepository = studentEnrollmentRepository;
        this.studentRepository = studentRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.academicYearRepository = academicYearRepository;
    }

    // Create enrollment
    public StudentEnrollmentResponse createEnrollment(
            StudentEnrollmentCreateRequest request) {

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        StudentGroup studentGroup = studentGroupRepository
                .findById(request.getStudentGroupId())
                .orElseThrow(() ->
                        new RuntimeException("Student group not found"));

        AcademicYear academicYear = academicYearRepository
                .findById(request.getAcademicYearId())
                .orElseThrow(() ->
                        new RuntimeException("Academic year not found"));

        StudentEnrollment enrollment = new StudentEnrollment();

        enrollment.setStudent(student);
        enrollment.setStudentGroup(studentGroup);
        enrollment.setAcademicYear(academicYear);
        enrollment.setEnrollmentDate(request.getEnrollmentDate());
        enrollment.setStatus(request.getStatus());

        StudentEnrollment savedEnrollment =
                studentEnrollmentRepository.save(enrollment);

        return convertToResponse(savedEnrollment);
    }

    // Get all enrollments
    public List<StudentEnrollmentResponse> getAllEnrollments() {

        return studentEnrollmentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // Get enrollment by ID
    public StudentEnrollmentResponse getEnrollmentById(Long id) {

        StudentEnrollment enrollment =
                studentEnrollmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Enrollment not found"));

        return convertToResponse(enrollment);
    }

    // Update enrollment
    public StudentEnrollmentResponse updateEnrollment(
            Long id,
            StudentEnrollmentCreateRequest request) {

        StudentEnrollment enrollment =
                studentEnrollmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Enrollment not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        StudentGroup studentGroup = studentGroupRepository
                .findById(request.getStudentGroupId())
                .orElseThrow(() ->
                        new RuntimeException("Student group not found"));

        AcademicYear academicYear = academicYearRepository
                .findById(request.getAcademicYearId())
                .orElseThrow(() ->
                        new RuntimeException("Academic year not found"));

        enrollment.setStudent(student);
        enrollment.setStudentGroup(studentGroup);
        enrollment.setAcademicYear(academicYear);
        enrollment.setEnrollmentDate(request.getEnrollmentDate());
        enrollment.setStatus(request.getStatus());

        StudentEnrollment updatedEnrollment =
                studentEnrollmentRepository.save(enrollment);

        return convertToResponse(updatedEnrollment);
    }

    // Delete enrollment
    public void deleteEnrollment(Long id) {

        if (!studentEnrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found");
        }

        studentEnrollmentRepository.deleteById(id);
    }

    // Convert Entity → Response
    private StudentEnrollmentResponse convertToResponse(
            StudentEnrollment enrollment) {

        Student student = enrollment.getStudent();
        StudentGroup studentGroup = enrollment.getStudentGroup();
        AcademicYear academicYear = enrollment.getAcademicYear();

        StudentEnrollmentResponse response =
                new StudentEnrollmentResponse();

        response.setId(enrollment.getId());

        // Student information
        response.setStudentId(student.getId());
        response.setStudentName(student.getName());
        response.setStudentCode(student.getStudentId());

        // Student Group information
        response.setStudentGroupId(studentGroup.getId());
        response.setStudentGroupName(studentGroup.getGroupName());
        response.setSectionName(
                studentGroup.getSection().getName()
        );
        response.setCourseName(
                studentGroup.getCourse().getCourseName()
        );
        response.setSemesterNumber(
                studentGroup.getSemester().getSemesterNumber()
        );
        response.setSemesterName(
                studentGroup.getSemester().getSemesterName()
        );

        // Academic Year
        response.setAcademicYearId(academicYear.getId());
        response.setAcademicYear(academicYear.getYear());

        // Enrollment information
        response.setEnrollmentDate(
                enrollment.getEnrollmentDate()
        );
        response.setStatus(
                enrollment.getStatus()
        );

        return response;
    }
}