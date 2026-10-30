package com.college.erp.dto;

public class SubjectResponse {

    private Long id;
    private String subjectCode;
    private String subjectName;
    private Integer credits;

    private Long courseId;
    private String courseName;

    private Long semesterId;
    private Integer semesterNumber;
    private String semesterName;

    public SubjectResponse() {
    }

    public SubjectResponse(
            Long id,
            String subjectCode,
            String subjectName,
            Integer credits,
            Long courseId,
            String courseName,
            Long semesterId,
            Integer semesterNumber,
            String semesterName) {

        this.id = id;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credits = credits;
        this.courseId = courseId;
        this.courseName = courseName;
        this.semesterId = semesterId;
        this.semesterNumber = semesterNumber;
        this.semesterName = semesterName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

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
}