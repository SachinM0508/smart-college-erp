package com.college.erp.service;

import com.college.erp.dto.FacultyQualificationCreateRequest;
import com.college.erp.dto.FacultyQualificationResponse;
import com.college.erp.entity.Faculty;
import com.college.erp.entity.FacultyQualification;
import com.college.erp.repository.FacultyQualificationRepository;
import com.college.erp.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyQualificationService {

    private final FacultyQualificationRepository qualificationRepository;
    private final FacultyRepository facultyRepository;

    public FacultyQualificationService(
            FacultyQualificationRepository qualificationRepository,
            FacultyRepository facultyRepository) {

        this.qualificationRepository = qualificationRepository;
        this.facultyRepository = facultyRepository;
    }

    // =========================
    // CREATE
    // =========================

    public FacultyQualificationResponse createQualification(
            FacultyQualificationCreateRequest request) {

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        FacultyQualification qualification = new FacultyQualification();

        qualification.setFaculty(faculty);
        qualification.setQualification(request.getQualification());
        qualification.setSpecialization(request.getSpecialization());
        qualification.setUniversity(request.getUniversity());
        qualification.setYearOfPassing(request.getYearOfPassing());
        qualification.setPercentage(request.getPercentage());

        FacultyQualification savedQualification =
                qualificationRepository.save(qualification);

        return convertToResponse(savedQualification);
    }

    // =========================
    // GET ALL
    // =========================

    public List<FacultyQualificationResponse> getAllQualifications() {

        return qualificationRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    // =========================
    // GET BY ID
    // =========================

    public FacultyQualificationResponse getQualificationById(Long id) {

        FacultyQualification qualification =
                qualificationRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Faculty qualification not found"
                                )
                        );

        return convertToResponse(qualification);
    }

    // =========================
    // UPDATE
    // =========================

    public FacultyQualificationResponse updateQualification(
            Long id,
            FacultyQualificationCreateRequest request) {

        FacultyQualification qualification =
                qualificationRepository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Faculty qualification not found"
                                )
                        );

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        qualification.setFaculty(faculty);
        qualification.setQualification(request.getQualification());
        qualification.setSpecialization(request.getSpecialization());
        qualification.setUniversity(request.getUniversity());
        qualification.setYearOfPassing(request.getYearOfPassing());
        qualification.setPercentage(request.getPercentage());

        FacultyQualification updatedQualification =
                qualificationRepository.save(qualification);

        return convertToResponse(updatedQualification);
    }

    // =========================
    // DELETE
    // =========================

    public void deleteQualification(Long id) {

        if (!qualificationRepository.existsById(id)) {
            throw new RuntimeException(
                    "Faculty qualification not found"
            );
        }

        qualificationRepository.deleteById(id);
    }

    // =========================
    // RESPONSE CONVERSION
    // =========================

    private FacultyQualificationResponse convertToResponse(
            FacultyQualification qualification) {

        FacultyQualificationResponse response =
                new FacultyQualificationResponse();

        Faculty faculty = qualification.getFaculty();

        response.setId(qualification.getId());

        response.setFacultyId(faculty.getId());
        response.setFacultyName(faculty.getName());
        response.setFacultyCode(faculty.getFacultyId());

        response.setQualification(
                qualification.getQualification()
        );

        response.setSpecialization(
                qualification.getSpecialization()
        );

        response.setUniversity(
                qualification.getUniversity()
        );

        response.setYearOfPassing(
                qualification.getYearOfPassing()
        );

        response.setPercentage(
                qualification.getPercentage()
        );

        return response;
    }
}