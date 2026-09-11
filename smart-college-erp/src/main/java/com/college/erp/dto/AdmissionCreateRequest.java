package com.college.erp.dto;

import com.college.erp.entity.AdmissionType;
import com.college.erp.entity.InstitutionType;
import com.college.erp.entity.SeatType;

import java.time.LocalDate;

public class AdmissionCreateRequest {

    private Long studentId;
    private Long courseId;
    private Long academicYearId;

    private AdmissionType admissionType;
    private SeatType seatType;
    private InstitutionType institutionType;

    private LocalDate admissionDate;

    public AdmissionCreateRequest() {
    }

    // Generate getters and setters for all fields

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public AdmissionType getAdmissionType() {
        return admissionType;
    }

    public void setAdmissionType(AdmissionType admissionType) {
        this.admissionType = admissionType;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public InstitutionType getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(InstitutionType institutionType) {
        this.institutionType = institutionType;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }
}