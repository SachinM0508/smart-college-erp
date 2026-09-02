package com.college.erp.service;

import com.college.erp.entity.Designation;
import com.college.erp.repository.DesignationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignationService {

    private final DesignationRepository designationRepository;

    public DesignationService(DesignationRepository designationRepository) {
        this.designationRepository = designationRepository;
    }

    // Create designation
    public Designation createDesignation(Designation designation) {
        return designationRepository.save(designation);
    }

    // Get all designations
    public List<Designation> getAllDesignations() {
        return designationRepository.findAll();
    }

    // Get designation by ID
    public Optional<Designation> getDesignationById(Long id) {
        return designationRepository.findById(id);
    }

    // Delete designation
    public void deleteDesignation(Long id) {
        designationRepository.deleteById(id);
    }
}

