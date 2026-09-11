package com.college.erp.service;

import com.college.erp.dto.AcademicYearCreateRequest;
import com.college.erp.dto.AcademicYearResponse;
import com.college.erp.entity.AcademicYear;
import com.college.erp.repository.AcademicYearRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcademicYearService {

    private final AcademicYearRepository academicYearRepository;

    public AcademicYearService(
            AcademicYearRepository academicYearRepository) {

        this.academicYearRepository = academicYearRepository;
    }

    // Create academic year
    public AcademicYearResponse createAcademicYear(
            AcademicYearCreateRequest request) {

        AcademicYear academicYear = new AcademicYear();

        academicYear.setYear(request.getYear());
        academicYear.setStartDate(request.getStartDate());
        academicYear.setEndDate(request.getEndDate());
        academicYear.setActive(request.getActive());

        AcademicYear savedAcademicYear =
                academicYearRepository.save(academicYear);

        return convertToResponse(savedAcademicYear);
    }

    // Get all academic years
    public List<AcademicYear> getAllAcademicYears() {
        return academicYearRepository.findAll();
    }

    // Get academic year by ID
    public Optional<AcademicYear> getAcademicYearById(Long id) {
        return academicYearRepository.findById(id);
    }

    // Update academic year
    public AcademicYearResponse updateAcademicYear(
            Long id,
            AcademicYearCreateRequest request) {

        AcademicYear academicYear =
                academicYearRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Academic year not found"));

        academicYear.setYear(request.getYear());
        academicYear.setStartDate(request.getStartDate());
        academicYear.setEndDate(request.getEndDate());
        academicYear.setActive(request.getActive());

        AcademicYear updatedAcademicYear =
                academicYearRepository.save(academicYear);

        return convertToResponse(updatedAcademicYear);
    }

    // Delete academic year
    public void deleteAcademicYear(Long id) {

        AcademicYear academicYear =
                academicYearRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Academic year not found"));

        academicYearRepository.delete(academicYear);
    }

    // Convert Entity → Response
    public AcademicYearResponse convertToResponse(
            AcademicYear academicYear) {

        AcademicYearResponse response =
                new AcademicYearResponse();

        response.setId(academicYear.getId());
        response.setYear(academicYear.getYear());
        response.setStartDate(academicYear.getStartDate());
        response.setEndDate(academicYear.getEndDate());
        response.setActive(academicYear.getActive());

        return response;
    }
}
