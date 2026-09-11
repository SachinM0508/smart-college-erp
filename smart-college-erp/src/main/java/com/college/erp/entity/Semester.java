package com.college.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "semesters",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"course_id", "semester_number"}
                )
        }
)
public class Semester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer semesterNumber;

    @Column(nullable = false)
    private String semesterName;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public Semester() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}