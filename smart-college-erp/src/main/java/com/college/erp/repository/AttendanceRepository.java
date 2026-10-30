package com.college.erp.repository;

import com.college.erp.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository
        extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findBySubjectId(Long subjectId);

    List<Attendance> findByStudentGroupId(Long studentGroupId);

    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);

    List<Attendance> findByStudentIdAndSubjectId(
            Long studentId,
            Long subjectId
    );

    boolean existsByStudentIdAndSubjectIdAndAttendanceDate(
            Long studentId,
            Long subjectId,
            LocalDate attendanceDate
    );

    Optional<Attendance> findById(Long id);

    boolean existsByStudentIdAndSubjectIdAndAttendanceDateAndIdNot(
            Long studentId,
            Long subjectId,
            LocalDate attendanceDate,
            Long id
    );
}