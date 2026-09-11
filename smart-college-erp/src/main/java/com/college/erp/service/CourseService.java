package com.college.erp.service;

import com.college.erp.dto.CourseCreateRequest;
import com.college.erp.dto.CourseResponse;
import com.college.erp.entity.Course;
import com.college.erp.entity.Department;
import com.college.erp.repository.CourseRepository;
import com.college.erp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;

    public CourseService(
            CourseRepository courseRepository,
            DepartmentRepository departmentRepository) {

        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
    }

    // Create course
    public CourseResponse createCourse(CourseCreateRequest request) {

        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new RuntimeException("Course code already exists");
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        Course course = new Course();

        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDuration(request.getDuration());
        course.setDepartment(department);

        Course savedCourse = courseRepository.save(course);

        return convertToResponse(savedCourse);
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by ID
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // Update course
    public CourseResponse updateCourse(
            Long id,
            CourseCreateRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        // Check course code only if it is being changed
        if (!course.getCourseCode().equals(request.getCourseCode())
                && courseRepository.existsByCourseCode(
                request.getCourseCode())) {

            throw new RuntimeException("Course code already exists");
        }

        Department department = departmentRepository
                .findById(request.getDepartmentId())
                .orElseThrow(() ->
                        new RuntimeException("Department not found"));

        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDuration(request.getDuration());
        course.setDepartment(department);

        Course updatedCourse = courseRepository.save(course);

        return convertToResponse(updatedCourse);
    }

    // Delete course
    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        courseRepository.delete(course);
    }

    // Convert Entity → Response
    public CourseResponse convertToResponse(Course course) {

        CourseResponse response = new CourseResponse();

        response.setId(course.getId());
        response.setCourseCode(course.getCourseCode());
        response.setCourseName(course.getCourseName());
        response.setDuration(course.getDuration());

        if (course.getDepartment() != null) {
            response.setDepartmentId(
                    course.getDepartment().getId());

            response.setDepartmentName(
                    course.getDepartment().getName());
        }

        return response;
    }
}