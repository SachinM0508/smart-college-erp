package com.college.erp.service;

import com.college.erp.dto.FacultyPayrollCreateRequest;
import com.college.erp.dto.FacultyPayrollResponse;
import com.college.erp.entity.Faculty;
import com.college.erp.entity.FacultyPayroll;
import com.college.erp.repository.FacultyPayrollRepository;
import com.college.erp.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacultyPayrollService {

    private final FacultyPayrollRepository payrollRepository;
    private final FacultyRepository facultyRepository;

    public FacultyPayrollService(
            FacultyPayrollRepository payrollRepository,
            FacultyRepository facultyRepository) {

        this.payrollRepository = payrollRepository;
        this.facultyRepository = facultyRepository;
    }

    public FacultyPayrollResponse createPayroll(
            FacultyPayrollCreateRequest request) {

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        if (payrollRepository.existsByFacultyId(request.getFacultyId())) {
            throw new RuntimeException(
                    "Payroll already exists for this faculty"
            );
        }

        if (request.getPanNumber() != null
                && payrollRepository.existsByPanNumber(request.getPanNumber())) {
            throw new RuntimeException(
                    "PAN number already exists"
            );
        }

        FacultyPayroll payroll = new FacultyPayroll();

        payroll.setFaculty(faculty);
        payroll.setBasicSalary(request.getBasicSalary());
        payroll.setPayGrade(request.getPayGrade());
        payroll.setBankAccountNumber(request.getBankAccountNumber());
        payroll.setIfscCode(request.getIfscCode());
        payroll.setPanNumber(request.getPanNumber());
        payroll.setSalaryMode(request.getSalaryMode());

        FacultyPayroll savedPayroll =
                payrollRepository.save(payroll);

        return convertToResponse(savedPayroll);
    }

    public List<FacultyPayrollResponse> getAllPayrolls() {

        return payrollRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public FacultyPayrollResponse getPayrollById(Long id) {

        FacultyPayroll payroll =
                payrollRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Faculty payroll not found"
                                )
                        );

        return convertToResponse(payroll);
    }

    public FacultyPayrollResponse updatePayroll(
            Long id,
            FacultyPayrollCreateRequest request) {

        FacultyPayroll payroll =
                payrollRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Faculty payroll not found"
                                )
                        );

        Faculty faculty = facultyRepository.findById(request.getFacultyId())
                .orElseThrow(() ->
                        new RuntimeException("Faculty not found"));

        if (payrollRepository.existsByFacultyIdAndIdNot(
                request.getFacultyId(), id)) {

            throw new RuntimeException(
                    "Payroll already exists for this faculty"
            );
        }

        if (request.getPanNumber() != null
                && payrollRepository.existsByPanNumberAndIdNot(
                request.getPanNumber(), id)) {

            throw new RuntimeException(
                    "PAN number already exists"
            );
        }

        payroll.setFaculty(faculty);
        payroll.setBasicSalary(request.getBasicSalary());
        payroll.setPayGrade(request.getPayGrade());
        payroll.setBankAccountNumber(request.getBankAccountNumber());
        payroll.setIfscCode(request.getIfscCode());
        payroll.setPanNumber(request.getPanNumber());
        payroll.setSalaryMode(request.getSalaryMode());

        FacultyPayroll updatedPayroll =
                payrollRepository.save(payroll);

        return convertToResponse(updatedPayroll);
    }

    public void deletePayroll(Long id) {

        if (!payrollRepository.existsById(id)) {
            throw new RuntimeException(
                    "Faculty payroll not found"
            );
        }

        payrollRepository.deleteById(id);
    }

    private FacultyPayrollResponse convertToResponse(
            FacultyPayroll payroll) {

        FacultyPayrollResponse response =
                new FacultyPayrollResponse();

        Faculty faculty = payroll.getFaculty();

        response.setId(payroll.getId());

        response.setFacultyId(faculty.getId());
        response.setFacultyName(faculty.getName());
        response.setFacultyCode(faculty.getFacultyId());

        response.setBasicSalary(payroll.getBasicSalary());
        response.setPayGrade(payroll.getPayGrade());

        response.setBankAccountNumber(
                payroll.getBankAccountNumber()
        );

        response.setIfscCode(
                payroll.getIfscCode()
        );

        response.setPanNumber(
                payroll.getPanNumber()
        );

        response.setSalaryMode(
                payroll.getSalaryMode()
        );

        return response;
    }
}