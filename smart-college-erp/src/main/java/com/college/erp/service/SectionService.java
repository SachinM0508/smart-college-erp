package com.college.erp.service;

import com.college.erp.dto.SectionCreateRequest;
import com.college.erp.dto.SectionResponse;
import com.college.erp.entity.Section;
import com.college.erp.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public SectionResponse createSection(SectionCreateRequest request) {

        Section section = new Section();
        section.setName(request.getName());

        Section savedSection = sectionRepository.save(section);

        return convertToResponse(savedSection);
    }

    public List<SectionResponse> getAllSections() {

        return sectionRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    public SectionResponse getSectionById(Long id) {

        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        return convertToResponse(section);
    }

    public SectionResponse updateSection(
            Long id,
            SectionCreateRequest request) {

        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        section.setName(request.getName());

        Section updatedSection = sectionRepository.save(section);

        return convertToResponse(updatedSection);
    }

    public void deleteSection(Long id) {

        if (!sectionRepository.existsById(id)) {
            throw new RuntimeException("Section not found");
        }

        sectionRepository.deleteById(id);
    }

    private SectionResponse convertToResponse(Section section) {

        return new SectionResponse(
                section.getId(),
                section.getName()
        );
    }
}