package com.college.erp.service;

import com.college.erp.dto.AdmissionCreateRequest;
import com.college.erp.dto.AdmissionResponse;
import com.college.erp.entity.Admission;
import com.college.erp.entity.AcademicYear;
import com.college.erp.entity.Course;
import com.college.erp.entity.Student;
import com.college.erp.repository.AcademicYearRepository;
import com.college.erp.repository.AdmissionRepository;
import com.college.erp.repository.CourseRepository;
import com.college.erp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionService {

    private final AdmissionRepository admissionRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AcademicYearRepository academicYearRepository;

    public AdmissionService(
            AdmissionRepository admissionRepository,
            StudentRepository studentRepository,
            CourseRepository courseRepository,
            AcademicYearRepository academicYearRepository) {

        this.admissionRepository = admissionRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.academicYearRepository = academicYearRepository;
    }

    // Create admission
    public AdmissionResponse createAdmission(
            AdmissionCreateRequest request) {

        // Check student
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        // One student should have only one admission
        if (admissionRepository.existsByStudentId(student.getId())) {
            throw new RuntimeException("Admission already exists for this student");
        }

        // Find course
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        // Find academic year
        AcademicYear academicYear =
                academicYearRepository.findById(request.getAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Academic year not found"));

        // Create admission
        Admission admission = new Admission();

        admission.setStudent(student);
        admission.setCourse(course);
        admission.setAcademicYear(academicYear);
        admission.setAdmissionType(request.getAdmissionType());
        admission.setSeatType(request.getSeatType());
        admission.setInstitutionType(request.getInstitutionType());
        admission.setAdmissionDate(request.getAdmissionDate());

        Admission savedAdmission =
                admissionRepository.save(admission);

        return convertToResponse(savedAdmission);
    }

    // Get all admissions
    public List<Admission> getAllAdmissions() {
        return admissionRepository.findAll();
    }

    // Get admission by ID
    public Optional<Admission> getAdmissionById(Long id) {
        return admissionRepository.findById(id);
    }

    // Get admission by student
    public Optional<Admission> getAdmissionByStudentId(Long studentId) {
        return admissionRepository.findByStudentId(studentId);
    }


    // Update admission
    public AdmissionResponse updateAdmission(
            Long id,
            AdmissionCreateRequest request) {

        Admission admission = admissionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Admission not found"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        AcademicYear academicYear =
                academicYearRepository.findById(request.getAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Academic year not found"));

        // If student is being changed, make sure another admission
        // doesn't already belong to that student.
        if (!admission.getStudent().getId().equals(student.getId())
                && admissionRepository.existsByStudentId(student.getId())) {

            throw new RuntimeException(
                    "Admission already exists for this student");
        }

        admission.setStudent(student);
        admission.setCourse(course);
        admission.setAcademicYear(academicYear);
        admission.setAdmissionType(request.getAdmissionType());
        admission.setSeatType(request.getSeatType());
        admission.setInstitutionType(request.getInstitutionType());
        admission.setAdmissionDate(request.getAdmissionDate());

        Admission updatedAdmission =
                admissionRepository.save(admission);

        return convertToResponse(updatedAdmission);
    }

    // Delete admission
    public void deleteAdmission(Long id) {

        Admission admission = admissionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Admission not found"));

        admissionRepository.delete(admission);
    }

    // Convert Entity → Response
    public AdmissionResponse convertToResponse(Admission admission) {

        AdmissionResponse response = new AdmissionResponse();

        response.setId(admission.getId());

        if (admission.getStudent() != null) {
            response.setStudentId(admission.getStudent().getId());
            response.setStudentName(admission.getStudent().getName());
        }

        if (admission.getCourse() != null) {
            response.setCourseId(admission.getCourse().getId());
            response.setCourseName(admission.getCourse().getCourseName());
        }

        if (admission.getAcademicYear() != null) {
            response.setAcademicYearId(
                    admission.getAcademicYear().getId());

            /*
             * IMPORTANT:
             * This assumes AcademicYear has a field called "year".
             * If your AcademicYear uses a different field name,
             * we'll change this line.
             */
            response.setAcademicYear(
                    admission.getAcademicYear().getYear());
        }

        response.setAdmissionType(admission.getAdmissionType());
        response.setSeatType(admission.getSeatType());
        response.setInstitutionType(admission.getInstitutionType());
        response.setAdmissionDate(admission.getAdmissionDate());

        return response;
    }
}