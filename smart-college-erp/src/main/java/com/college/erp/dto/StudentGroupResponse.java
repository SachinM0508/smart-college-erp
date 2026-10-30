package com.college.erp.dto;

public class StudentGroupResponse {

    private Long id;
    private String groupName;

    private Long sectionId;
    private String sectionName;

    private Long courseId;
    private String courseName;

    private Long semesterId;
    private Integer semesterNumber;
    private String semesterName;

    private Long academicYearId;
    private String academicYear;

    public StudentGroupResponse() {
    }

    public StudentGroupResponse(
            Long id,
            String groupName,
            Long sectionId,
            String sectionName,
            Long courseId,
            String courseName,
            Long semesterId,
            Integer semesterNumber,
            String semesterName,
            Long academicYearId,
            String academicYear) {

        this.id = id;
        this.groupName = groupName;
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.courseId = courseId;
        this.courseName = courseName;
        this.semesterId = semesterId;
        this.semesterNumber = semesterNumber;
        this.semesterName = semesterName;
        this.academicYearId = academicYearId;
        this.academicYear = academicYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
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

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
}