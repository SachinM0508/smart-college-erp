package com.college.erp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "faculty_payroll")
public class FacultyPayroll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "faculty_id", nullable = false, unique = true)
    private Faculty faculty;

    // Salary
    @Column(nullable = false)
    private Double basicSalary;

    private String payGrade;

    // Bank Details
    private String bankAccountNumber;

    private String ifscCode;

    // Tax / Identification
    private String panNumber;

    // Salary Payment
    @Enumerated(EnumType.STRING)
    private SalaryMode salaryMode;

    public FacultyPayroll() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public Double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(Double basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getPayGrade() {
        return payGrade;
    }

    public void setPayGrade(String payGrade) {
        this.payGrade = payGrade;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public SalaryMode getSalaryMode() {
        return salaryMode;
    }

    public void setSalaryMode(SalaryMode salaryMode) {
        this.salaryMode = salaryMode;
    }
}