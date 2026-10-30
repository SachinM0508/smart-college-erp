package com.college.erp.dto;

import com.college.erp.entity.SalaryMode;

public class FacultyPayrollCreateRequest {

    private Long facultyId;

    private Double basicSalary;

    private String payGrade;

    private String bankAccountNumber;

    private String ifscCode;

    private String panNumber;

    private SalaryMode salaryMode;

    public FacultyPayrollCreateRequest() {
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
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