package com.college.erp.service;

import com.college.erp.dto.SemesterCreateRequest;
import com.college.erp.dto.SemesterResponse;
import com.college.erp.entity.Course;
import com.college.erp.entity.Semester;
import com.college.erp.repository.CourseRepository;
import com.college.erp.repository.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SemesterService {

    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;

    public SemesterService(
            SemesterRepository semesterRepository,
            CourseRepository courseRepository) {

        this.semesterRepository = semesterRepository;
        this.courseRepository = courseRepository;
    }

    // Create semester
    public SemesterResponse createSemester(
            SemesterCreateRequest request) {

        if (semesterRepository.existsByCourseIdAndSemesterNumber(
                request.getCourseId(),
                request.getSemesterNumber())) {

            throw new RuntimeException(
                    "Semester already exists for this course");
        }

        Course course = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        Semester semester = new Semester();

        semester.setSemesterNumber(request.getSemesterNumber());
        semester.setSemesterName(request.getSemesterName());
        semester.setCourse(course);

        Semester savedSemester =
                semesterRepository.save(semester);

        return convertToResponse(savedSemester);
    }

    // Get all semesters
    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    // Get semester by ID
    public Optional<Semester> getSemesterById(Long id) {
        return semesterRepository.findById(id);
    }

    // Update semester
    public SemesterResponse updateSemester(
            Long id,
            SemesterCreateRequest request) {

        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Semester not found"));

        if (!semester.getCourse().getId().equals(request.getCourseId())
                || !semester.getSemesterNumber()
                .equals(request.getSemesterNumber())) {

            if (semesterRepository
                    .existsByCourseIdAndSemesterNumber(
                            request.getCourseId(),
                            request.getSemesterNumber())) {

                throw new RuntimeException(
                        "Semester already exists for this course");
            }
        }

        Course course = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        semester.setSemesterNumber(request.getSemesterNumber());
        semester.setSemesterName(request.getSemesterName());
        semester.setCourse(course);

        Semester updatedSemester =
                semesterRepository.save(semester);

        return convertToResponse(updatedSemester);
    }

    // Delete semester
    public void deleteSemester(Long id) {

        Semester semester = semesterRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Semester not found"));

        semesterRepository.delete(semester);
    }

    // Convert Entity → Response
    public SemesterResponse convertToResponse(
            Semester semester) {

        SemesterResponse response =
                new SemesterResponse();

        response.setId(semester.getId());
        response.setSemesterNumber(
                semester.getSemesterNumber());
        response.setSemesterName(
                semester.getSemesterName());

        if (semester.getCourse() != null) {
            response.setCourseId(
                    semester.getCourse().getId());

            response.setCourseName(
                    semester.getCourse().getCourseName());
        }

        return response;
    }
}