package com.college.erp.service;

import com.college.erp.dto.ExamCreateRequest;
import com.college.erp.dto.ExamResponse;
import com.college.erp.entity.*;
import com.college.erp.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final AcademicYearRepository academicYearRepository;
    private final CourseRepository courseRepository;
    private final SemesterRepository semesterRepository;
    private final StudentGroupRepository studentGroupRepository;

    public ExamService(
            ExamRepository examRepository,
            AcademicYearRepository academicYearRepository,
            CourseRepository courseRepository,
            SemesterRepository semesterRepository,
            StudentGroupRepository studentGroupRepository
    ) {
        this.examRepository = examRepository;
        this.academicYearRepository = academicYearRepository;
        this.courseRepository = courseRepository;
        this.semesterRepository = semesterRepository;
        this.studentGroupRepository = studentGroupRepository;
    }

    public ExamResponse createExam(ExamCreateRequest request) {

        validateRequest(request);

        if (examRepository.existsByExamNameAndStudentGroupIdAndAcademicYearId(
                request.getExamName(),
                request.getStudentGroupId(),
                request.getAcademicYearId()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Exam already exists for this student group and academic year"
            );
        }

        AcademicYear academicYear = academicYearRepository
                .findById(request.getAcademicYearId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Academic year not found"
                ));

        Course course = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found"
                ));

        Semester semester = semesterRepository
                .findById(request.getSemesterId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Semester not found"
                ));

        StudentGroup studentGroup = studentGroupRepository
                .findById(request.getStudentGroupId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student group not found"
                ));

        Exam exam = new Exam();

        exam.setExamName(request.getExamName());
        exam.setExamType(request.getExamType());
        exam.setAcademicYear(academicYear);
        exam.setCourse(course);
        exam.setSemester(semester);
        exam.setStudentGroup(studentGroup);
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());
        exam.setMaxMarks(request.getMaxMarks());

        return convertToResponse(examRepository.save(exam));
    }

    public List<ExamResponse> getAllExams() {

        return examRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public ExamResponse getExamById(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exam not found"
                ));

        return convertToResponse(exam);
    }

    public ExamResponse updateExam(
            Long id,
            ExamCreateRequest request
    ) {

        validateRequest(request);

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exam not found"
                ));

        if (examRepository
                .existsByExamNameAndStudentGroupIdAndAcademicYearIdAndIdNot(
                        request.getExamName(),
                        request.getStudentGroupId(),
                        request.getAcademicYearId(),
                        id
                )) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Exam already exists for this student group and academic year"
            );
        }

        AcademicYear academicYear = academicYearRepository
                .findById(request.getAcademicYearId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Academic year not found"
                ));

        Course course = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found"
                ));

        Semester semester = semesterRepository
                .findById(request.getSemesterId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Semester not found"
                ));

        StudentGroup studentGroup = studentGroupRepository
                .findById(request.getStudentGroupId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student group not found"
                ));

        exam.setExamName(request.getExamName());
        exam.setExamType(request.getExamType());
        exam.setAcademicYear(academicYear);
        exam.setCourse(course);
        exam.setSemester(semester);
        exam.setStudentGroup(studentGroup);
        exam.setStartDate(request.getStartDate());
        exam.setEndDate(request.getEndDate());
        exam.setMaxMarks(request.getMaxMarks());

        return convertToResponse(examRepository.save(exam));
    }

    public void deleteExam(Long id) {

        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Exam not found"
                ));

        examRepository.delete(exam);
    }

    public List<ExamResponse> getExamsByAcademicYear(Long academicYearId) {

        return examRepository.findByAcademicYearId(academicYearId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ExamResponse> getExamsByCourse(Long courseId) {

        return examRepository.findByCourseId(courseId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ExamResponse> getExamsBySemester(Long semesterId) {

        return examRepository.findBySemesterId(semesterId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ExamResponse> getExamsByStudentGroup(Long studentGroupId) {

        return examRepository.findByStudentGroupId(studentGroupId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<ExamResponse> getExamsByExamType(ExamType examType) {

        return examRepository.findByExamType(examType)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private void validateRequest(ExamCreateRequest request) {

        if (request.getExamName() == null ||
                request.getExamName().isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exam name is required"
            );
        }

        if (request.getExamType() == null) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Exam type is required"
            );
        }

        if (request.getMaxMarks() == null ||
                request.getMaxMarks() <= 0) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Maximum marks must be greater than zero"
            );
        }

        if (request.getStartDate() != null &&
                request.getEndDate() != null &&
                request.getEndDate().isBefore(request.getStartDate())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "End date cannot be before start date"
            );
        }

        AcademicYear academicYear = academicYearRepository
                .findById(request.getAcademicYearId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Academic year not found"
                ));

        Course course = courseRepository
                .findById(request.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course not found"
                ));

        Semester semester = semesterRepository
                .findById(request.getSemesterId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Semester not found"
                ));

        StudentGroup studentGroup = studentGroupRepository
                .findById(request.getStudentGroupId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Student group not found"
                ));

        if (!semester.getCourse().getId().equals(course.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Semester does not belong to the selected course"
            );
        }

        if (!studentGroup.getCourse().getId().equals(course.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student group does not belong to the selected course"
            );
        }

        if (!studentGroup.getSemester().getId().equals(semester.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student group does not belong to the selected semester"
            );
        }

        if (!studentGroup.getAcademicYear().getId()
                .equals(academicYear.getId())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Student group does not belong to the selected academic year"
            );
        }
    }

    private ExamResponse convertToResponse(Exam exam) {

        ExamResponse response = new ExamResponse();

        response.setId(exam.getId());
        response.setExamName(exam.getExamName());
        response.setExamType(exam.getExamType());

        response.setAcademicYearId(
                exam.getAcademicYear().getId()
        );

        response.setAcademicYear(
                exam.getAcademicYear().getYear()
        );

        response.setCourseId(
                exam.getCourse().getId()
        );

        response.setCourseName(
                exam.getCourse().getCourseName()
        );

        response.setSemesterId(
                exam.getSemester().getId()
        );

        response.setSemesterNumber(
                exam.getSemester().getSemesterNumber()
        );

        response.setSemesterName(
                exam.getSemester().getSemesterName()
        );

        response.setStudentGroupId(
                exam.getStudentGroup().getId()
        );

        response.setStudentGroupName(
                exam.getStudentGroup().getGroupName()
        );

        response.setSectionName(
                exam.getStudentGroup().getSection().getName()
        );

        response.setStartDate(exam.getStartDate());
        response.setEndDate(exam.getEndDate());
        response.setMaxMarks(exam.getMaxMarks());

        return response;
    }
}