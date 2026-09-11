package com.college.erp.service;

import com.college.erp.dto.SubjectCreateRequest;
import com.college.erp.dto.SubjectResponse;
import com.college.erp.entity.Course;
import com.college.erp.entity.Semester;
import com.college.erp.entity.Subject;
import com.college.erp.repository.CourseRepository;
import com.college.erp.repository.SemesterRepository;
import com.college.erp.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;

    public SubjectService(
            SubjectRepository subjectRepository,
            CourseRepository courseRepository,
            SemesterRepository semesterRepository) {

        this.subjectRepository = subjectRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
    }

    public SubjectResponse createSubject(SubjectCreateRequest request) {

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        Subject subject = new Subject();

        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setCredits(request.getCredits());
        subject.setCourse(course);
        subject.setSemester(semester);

        Subject savedSubject = subjectRepository.save(subject);

        return convertToResponse(savedSubject);
    }

    public List<SubjectResponse> getAllSubjects() {

        return subjectRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public SubjectResponse getSubjectById(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        return convertToResponse(subject);
    }

    public SubjectResponse updateSubject(
            Long id,
            SubjectCreateRequest request) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Semester semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setCredits(request.getCredits());
        subject.setCourse(course);
        subject.setSemester(semester);

        Subject updatedSubject = subjectRepository.save(subject);

        return convertToResponse(updatedSubject);
    }

    public void deleteSubject(Long id) {

        if (!subjectRepository.existsById(id)) {
            throw new RuntimeException("Subject not found");
        }

        subjectRepository.deleteById(id);
    }

    private SubjectResponse convertToResponse(Subject subject) {

        return new SubjectResponse(
                subject.getId(),
                subject.getSubjectCode(),
                subject.getSubjectName(),
                subject.getCredits(),

                subject.getCourse().getId(),
                subject.getCourse().getCourseName(),

                subject.getSemester().getId(),
                subject.getSemester().getSemesterNumber(),
                subject.getSemester().getSemesterName()
        );
    }
}