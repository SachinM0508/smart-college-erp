package com.college.erp.dto;

public class SemesterCreateRequest {

    private Integer semesterNumber;
    private String semesterName;
    private Long courseId;

    public SemesterCreateRequest() {
    }

    // Generate getters and setters

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(Integer semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}