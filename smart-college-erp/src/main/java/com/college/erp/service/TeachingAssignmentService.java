package com.college.erp.service;

import com.college.erp.dto.TeachingAssignmentCreateRequest;
import com.college.erp.dto.TeachingAssignmentResponse;
import com.college.erp.entity.AcademicYear;
import com.college.erp.entity.Faculty;
import com.college.erp.entity.Semester;
import com.college.erp.entity.StudentGroup;
import com.college.erp.entity.Subject;
import com.college.erp.entity.TeachingAssignment;
import com.college.erp.repository.AcademicYearRepository;
import com.college.erp.repository.FacultyRepository;
import com.college.erp.repository.SemesterRepository;
import com.college.erp.repository.StudentGroupRepository;
import com.college.erp.repository.SubjectRepository;
import com.college.erp.repository.TeachingAssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeachingAssignmentService {

    private final TeachingAssignmentRepository assignmentRepository;
    private final FacultyRepository facultyRepository;
    private final SubjectRepository subjectRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final AcademicYearRepository academicYearRepository;

    public TeachingAssignmentService(
            TeachingAssignmentRepository assignmentRepository,
            FacultyRepository facultyRepository,
            SubjectRepository subjectRepository,
            StudentGroupRepository studentGroupRepository,
            AcademicYearRepository academicYearRepository) {

        this.assignmentRepository = assignmentRepository;
        this.facultyRepository = facultyRepository;
        this.subjectRepository = subjectRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.academicYearRepository = academicYearRepository;
    }

    public TeachingAssignmentResponse createAssignment(
            TeachingAssignmentCreateRequest request) {

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        StudentGroup studentGroup =
                studentGroupRepository.findById(request.getStudentGroupId())
                        .orElseThrow(() ->
                                new RuntimeException("Student group not found"));

        AcademicYear academicYear =
                academicYearRepository.findById(request.getAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Academic year not found"));

        if (assignmentRepository
                .existsByFacultyIdAndSubjectIdAndStudentGroupIdAndAcademicYearId(
                        request.getFacultyId(),
                        request.getSubjectId(),
                        request.getStudentGroupId(),
                        request.getAcademicYearId())) {

            throw new RuntimeException(
                    "Teaching assignment already exists");
        }

        TeachingAssignment assignment = new TeachingAssignment();

        assignment.setFaculty(faculty);
        assignment.setSubject(subject);
        assignment.setStudentGroup(studentGroup);
        assignment.setAcademicYear(academicYear);

        TeachingAssignment savedAssignment =
                assignmentRepository.save(assignment);

        return convertToResponse(savedAssignment);
    }

    public List<TeachingAssignmentResponse> getAllAssignments() {

        return assignmentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public TeachingAssignmentResponse getAssignmentById(Long id) {

        TeachingAssignment assignment =
                assignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Teaching assignment not found"));

        return convertToResponse(assignment);
    }

    public TeachingAssignmentResponse updateAssignment(
            Long id,
            TeachingAssignmentCreateRequest request) {

        TeachingAssignment assignment =
                assignmentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Teaching assignment not found"));

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));

        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        StudentGroup studentGroup =
                studentGroupRepository.findById(request.getStudentGroupId())
                        .orElseThrow(() ->
                                new RuntimeException("Student group not found"));

        AcademicYear academicYear =
                academicYearRepository.findById(request.getAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Academic year not found"));

        if (assignmentRepository
                .existsByFacultyIdAndSubjectIdAndStudentGroupIdAndAcademicYearIdAndIdNot(
                        request.getFacultyId(),
                        request.getSubjectId(),
                        request.getStudentGroupId(),
                        request.getAcademicYearId(),
                        id)) {

            throw new RuntimeException(
                    "Teaching assignment already exists");
        }

        assignment.setFaculty(faculty);
        assignment.setSubject(subject);
        assignment.setStudentGroup(studentGroup);
        assignment.setAcademicYear(academicYear);

        TeachingAssignment updatedAssignment =
                assignmentRepository.save(assignment);

        return convertToResponse(updatedAssignment);
    }

    public void deleteAssignment(Long id) {

        if (!assignmentRepository.existsById(id)) {
            throw new RuntimeException(
                    "Teaching assignment not found");
        }

        assignmentRepository.deleteById(id);
    }

    private TeachingAssignmentResponse convertToResponse(
            TeachingAssignment assignment) {

        TeachingAssignmentResponse response =
                new TeachingAssignmentResponse();

        Faculty faculty = assignment.getFaculty();
        Subject subject = assignment.getSubject();
        StudentGroup studentGroup = assignment.getStudentGroup();
        AcademicYear academicYear = assignment.getAcademicYear();

        response.setId(assignment.getId());

        // Faculty
        response.setFacultyId(faculty.getId());
        response.setFacultyName(faculty.getName());
        response.setFacultyCode(faculty.getFacultyId());

        // Subject
        response.setSubjectId(subject.getId());
        response.setSubjectCode(subject.getSubjectCode());
        response.setSubjectName(subject.getSubjectName());

        // Student Group
        response.setStudentGroupId(studentGroup.getId());
        response.setStudentGroupName(studentGroup.getGroupName());

        if (studentGroup.getSection() != null) {
            response.setSectionName(
                    studentGroup.getSection().getName());
        }

        // Academic Year
        response.setAcademicYearId(academicYear.getId());
        response.setAcademicYear(academicYear.getYear());

        // Course
        if (studentGroup.getCourse() != null) {
            response.setCourseId(
                    studentGroup.getCourse().getId());

            response.setCourseName(
                    studentGroup.getCourse().getCourseName());
        }

        // Semester
        Semester semester = studentGroup.getSemester();

        if (semester != null) {
            response.setSemesterId(semester.getId());
            response.setSemesterNumber(
                    semester.getSemesterNumber());
            response.setSemesterName(
                    semester.getSemesterName());
        }

        return response;
    }
}