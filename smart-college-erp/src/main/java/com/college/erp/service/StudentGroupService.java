package com.college.erp.service;

import com.college.erp.dto.StudentGroupCreateRequest;
import com.college.erp.dto.StudentGroupResponse;
import com.college.erp.entity.AcademicYear;
import com.college.erp.entity.Course;
import com.college.erp.entity.Section;
import com.college.erp.entity.Semester;
import com.college.erp.entity.StudentGroup;
import com.college.erp.repository.AcademicYearRepository;
import com.college.erp.repository.CourseRepository;
import com.college.erp.repository.SectionRepository;
import com.college.erp.repository.SemesterRepository;
import com.college.erp.repository.StudentGroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentGroupService {

    private final StudentGroupRepository studentGroupRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final AcademicYearRepository academicYearRepository;
    private final SectionRepository sectionRepository;

    public StudentGroupService(
            StudentGroupRepository studentGroupRepository,
            CourseRepository courseRepository,
            SemesterRepository semesterRepository,
            AcademicYearRepository academicYearRepository,
            SectionRepository sectionRepository) {

        this.studentGroupRepository = studentGroupRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.academicYearRepository = academicYearRepository;
        this.sectionRepository = sectionRepository;
    }

    // CREATE
    public StudentGroupResponse createStudentGroup(
            StudentGroupCreateRequest request) {

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() ->
                        new RuntimeException("Semester not found"));

        AcademicYear academicYear =
                academicYearRepository.findById(request.getAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Academic year not found"));

        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() ->
                        new RuntimeException("Section not found"));

        StudentGroup studentGroup = new StudentGroup();

        studentGroup.setGroupName(request.getGroupName());
        studentGroup.setSection(section);
        studentGroup.setCourse(course);
        studentGroup.setSemester(semester);
        studentGroup.setAcademicYear(academicYear);

        StudentGroup savedGroup =
                studentGroupRepository.save(studentGroup);

        return convertToResponse(savedGroup);
    }

    // GET ALL
    public List<StudentGroupResponse> getAllStudentGroups() {

        return studentGroupRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // GET BY ID
    public StudentGroupResponse getStudentGroupById(Long id) {

        StudentGroup studentGroup =
                studentGroupRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student group not found"));

        return convertToResponse(studentGroup);
    }

    // UPDATE
    public StudentGroupResponse updateStudentGroup(
            Long id,
            StudentGroupCreateRequest request) {

        StudentGroup studentGroup =
                studentGroupRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Student group not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        Semester semester =
                semesterRepository.findById(request.getSemesterId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Semester not found"));

        AcademicYear academicYear =
                academicYearRepository.findById(
                                request.getAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Academic year not found"));

        Section section =
                sectionRepository.findById(request.getSectionId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Section not found"));

        studentGroup.setGroupName(request.getGroupName());
        studentGroup.setSection(section);
        studentGroup.setCourse(course);
        studentGroup.setSemester(semester);
        studentGroup.setAcademicYear(academicYear);

        StudentGroup updatedGroup =
                studentGroupRepository.save(studentGroup);

        return convertToResponse(updatedGroup);
    }

    // DELETE
    public void deleteStudentGroup(Long id) {

        if (!studentGroupRepository.existsById(id)) {
            throw new RuntimeException(
                    "Student group not found");
        }

        studentGroupRepository.deleteById(id);
    }

    // CONVERT ENTITY TO RESPONSE
    private StudentGroupResponse convertToResponse(
            StudentGroup studentGroup) {

        return new StudentGroupResponse(
                studentGroup.getId(),
                studentGroup.getGroupName(),

                studentGroup.getSection().getId(),
                studentGroup.getSection().getName(),

                studentGroup.getCourse().getId(),
                studentGroup.getCourse().getCourseName(),

                studentGroup.getSemester().getId(),
                studentGroup.getSemester().getSemesterNumber(),
                studentGroup.getSemester().getSemesterName(),

                studentGroup.getAcademicYear().getId(),
                studentGroup.getAcademicYear().getYear()
        );
    }
    }