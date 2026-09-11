package com.college.erp.dto;

import java.time.LocalDate;

public class AcademicYearCreateRequest {

    private String year;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean active;

    public AcademicYearCreateRequest() {
    }

    // Generate getters and setters

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}