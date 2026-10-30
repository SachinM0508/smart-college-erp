package com.college.erp.service;

import com.college.erp.dto.AttendanceCreateRequest;
import com.college.erp.dto.AttendanceResponse;
import com.college.erp.entity.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import com.college.erp.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final AcademicYearRepository academicYearRepository;
    private final FacultyRepository facultyRepository;
    private final TeachingAssignmentRepository teachingAssignmentRepository;
    private final UserRepository userRepository;

    public AttendanceService(
            AttendanceRepository attendanceRepository,
            StudentRepository studentRepository,
            SubjectRepository subjectRepository,
            StudentGroupRepository studentGroupRepository,
            AcademicYearRepository academicYearRepository,
            FacultyRepository facultyRepository,
            TeachingAssignmentRepository teachingAssignmentRepository,
            UserRepository userRepository) {

        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.academicYearRepository = academicYearRepository;
        this.facultyRepository = facultyRepository;
        this.teachingAssignmentRepository = teachingAssignmentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public AttendanceResponse createAttendance(
            AttendanceCreateRequest request) {

        /*
         * Prevent duplicate attendance for the same
         * student, subject and date.
         */
        if (attendanceRepository
                .existsByStudentIdAndSubjectIdAndAttendanceDate(
                        request.getStudentId(),
                        request.getSubjectId(),
                        request.getAttendanceDate())) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Attendance already exists for this student, subject and date"
            );
        }

        /*
         * Fetch student.
         */
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() ->
                        new RuntimeException("Student not found"));

        /*
         * Fetch subject.
         */
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() ->
                        new RuntimeException("Subject not found"));

        /*
         * Fetch student group.
         */
        StudentGroup studentGroup =
                studentGroupRepository.findById(request.getStudentGroupId())
                        .orElseThrow(() ->
                                new RuntimeException("Student group not found"));

        /*
         * Fetch academic year.
         */
        AcademicYear academicYear =
                academicYearRepository.findById(request.getAcademicYearId())
                        .orElseThrow(() ->
                                new RuntimeException("Academic year not found"));

        /*
         * Get the currently authenticated user.
         */
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("User is not authenticated");
        }

        /*
         * Get logged-in user's email from the JWT.
         */
        String email = authentication.getName();

        /*
         * Find the User account.
         */
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Logged-in user not found"));

        /*
         * Find the Faculty profile linked to this User.
         */
        Faculty faculty =
                facultyRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Faculty profile not found for logged-in user"));

        /*
         * Validate that the student belongs to
         * the selected student group.
         */
        if (student.getStudentGroup() == null ||
                !student.getStudentGroup().getId()
                        .equals(studentGroup.getId())) {

            throw new RuntimeException(
                    "Student does not belong to the selected student group");
        }

        /*
         * Validate that the subject belongs to
         * the same course as the student group.
         */
        if (!subject.getCourse().getId()
                .equals(studentGroup.getCourse().getId())) {

            throw new RuntimeException(
                    "Subject does not belong to the selected course");
        }

        /*
         * Validate that the subject belongs to
         * the same semester as the student group.
         */
        if (!subject.getSemester().getId()
                .equals(studentGroup.getSemester().getId())) {

            throw new RuntimeException(
                    "Subject does not belong to the selected semester");
        }

        /*
         * Validate that the logged-in faculty is assigned
         * to this subject, student group and academic year.
         */
        boolean assigned =
                teachingAssignmentRepository
                        .existsByFacultyIdAndSubjectIdAndStudentGroupIdAndAcademicYearId(
                                faculty.getId(),
                                subject.getId(),
                                studentGroup.getId(),
                                academicYear.getId());

        if (!assigned) {

            throw new RuntimeException(
                    "Faculty is not assigned to this subject and student group");
        }

        /*
         * Create attendance record.
         */
        Attendance attendance = new Attendance();

        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setStudentGroup(studentGroup);
        attendance.setAcademicYear(academicYear);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setStatus(request.getStatus());

        /*
         * Faculty is taken from the authenticated user,
         * not from the request body.
         */
        attendance.setMarkedByFaculty(faculty);

        /*
         * Save attendance.
         */
        Attendance savedAttendance =
                attendanceRepository.save(attendance);

        /*
         * Convert entity to response.
         */
        return convertToResponse(savedAttendance);
    }

    @Transactional
    public AttendanceResponse updateAttendance(
            Long id,
            AttendanceCreateRequest request) {

        Attendance attendance =
                attendanceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Attendance not found"));

        // Check duplicate attendance, excluding the current record
        if (attendanceRepository
                .existsByStudentIdAndSubjectIdAndAttendanceDateAndIdNot(
                        request.getStudentId(),
                        request.getSubjectId(),
                        request.getAttendanceDate(),
                        id)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Attendance already exists for this student, subject and date"
            );
        }

        Student student =
                studentRepository.findById(request.getStudentId())
                        .orElseThrow(() ->
                                new RuntimeException("Student not found"));

        Subject subject =
                subjectRepository.findById(request.getSubjectId())
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

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("User is not authenticated");
        }

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Logged-in user not found"));

        Faculty faculty =
                facultyRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Faculty profile not found for logged-in user"));

        // Student must belong to selected group
        if (student.getStudentGroup() == null ||
                !student.getStudentGroup().getId()
                        .equals(studentGroup.getId())) {

            throw new RuntimeException(
                    "Student does not belong to the selected student group");
        }

        // Subject must belong to selected course
        if (!subject.getCourse().getId()
                .equals(studentGroup.getCourse().getId())) {

            throw new RuntimeException(
                    "Subject does not belong to the selected course");
        }

        // Subject must belong to selected semester
        if (!subject.getSemester().getId()
                .equals(studentGroup.getSemester().getId())) {

            throw new RuntimeException(
                    "Subject does not belong to the selected semester");
        }

        // Faculty must be assigned to this subject and group
        boolean assigned =
                teachingAssignmentRepository
                        .existsByFacultyIdAndSubjectIdAndStudentGroupIdAndAcademicYearId(
                                faculty.getId(),
                                subject.getId(),
                                studentGroup.getId(),
                                academicYear.getId());

        if (!assigned) {
            throw new RuntimeException(
                    "Faculty is not assigned to this subject and student group");
        }

        // Update attendance
        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setStudentGroup(studentGroup);
        attendance.setAcademicYear(academicYear);
        attendance.setAttendanceDate(request.getAttendanceDate());
        attendance.setStatus(request.getStatus());

        // Keep the faculty who performed the latest update
        attendance.setMarkedByFaculty(faculty);

        Attendance updatedAttendance =
                attendanceRepository.save(attendance);

        return convertToResponse(updatedAttendance);
    }

    @Transactional
    public void deleteAttendance(Long id) {

        Attendance attendance =
                attendanceRepository.findById(id)
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Attendance not found"
                                ));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("User is not authenticated");
        }

        String email = authentication.getName();

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(() ->
                                new RuntimeException("Logged-in user not found"));

        Faculty faculty =
                facultyRepository.findByUserId(user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Faculty profile not found for logged-in user"));

        // Get the attendance's subject, group and academic year
        Subject subject = attendance.getSubject();
        StudentGroup studentGroup = attendance.getStudentGroup();
        AcademicYear academicYear = attendance.getAcademicYear();

        // Verify that the logged-in faculty is assigned
        // to this subject and student group
        boolean assigned =
                teachingAssignmentRepository
                        .existsByFacultyIdAndSubjectIdAndStudentGroupIdAndAcademicYearId(
                                faculty.getId(),
                                subject.getId(),
                                studentGroup.getId(),
                                academicYear.getId());

        if (!assigned) {
            throw new RuntimeException(
                    "Faculty is not assigned to this attendance record");
        }

        attendanceRepository.delete(attendance);
    }

    public List<AttendanceResponse> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public AttendanceResponse getAttendanceById(Long id) {

        Attendance attendance =
                attendanceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Attendance not found"));

        return convertToResponse(attendance);
    }

    public List<AttendanceResponse> getAttendanceByStudent(
            Long studentId) {

        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<AttendanceResponse> getAttendanceBySubject(
            Long subjectId) {

        return attendanceRepository.findBySubjectId(subjectId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public List<AttendanceResponse> getAttendanceByGroup(
            Long studentGroupId) {

        return attendanceRepository.findByStudentGroupId(studentGroupId)
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    private AttendanceResponse convertToResponse(
            Attendance attendance) {

        AttendanceResponse response =
                new AttendanceResponse();

        response.setId(attendance.getId());

        /*
         * Student details.
         */
        Student student = attendance.getStudent();

        response.setStudentId(student.getId());
        response.setStudentCode(student.getStudentId());
        response.setStudentName(student.getName());

        /*
         * Subject details.
         */
        Subject subject = attendance.getSubject();

        response.setSubjectId(subject.getId());
        response.setSubjectCode(subject.getSubjectCode());
        response.setSubjectName(subject.getSubjectName());

        /*
         * Student group details.
         */
        StudentGroup group = attendance.getStudentGroup();

        response.setStudentGroupId(group.getId());
        response.setStudentGroupName(group.getGroupName());

        if (group.getSection() != null) {
            response.setSectionName(
                    group.getSection().getName());
        }

        /*
         * Academic year details.
         */
        AcademicYear academicYear =
                attendance.getAcademicYear();

        response.setAcademicYearId(academicYear.getId());
        response.setAcademicYear(academicYear.getYear());

        /*
         * Attendance details.
         */
        response.setAttendanceDate(
                attendance.getAttendanceDate());

        response.setStatus(
                attendance.getStatus());

        /*
         * Faculty details.
         */
        Faculty faculty =
                attendance.getMarkedByFaculty();

        response.setFacultyId(faculty.getId());
        response.setFacultyCode(faculty.getFacultyId());
        response.setFacultyName(faculty.getName());

        return response;
    }
}