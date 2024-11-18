package com.Project.GestionsFormation.Service.impl;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Repository.FormationRepository;
import com.Project.GestionsFormation.Service.FormationService;
import com.Project.GestionsFormation.dto.FormationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationServiceImplementation implements FormationService {
    private final FormationRepository formationRepository;

    @Autowired
    public FormationServiceImplementation(FormationRepository formationRepository) {
        this.formationRepository = formationRepository;
    }

    @Override
    public List<FormationDTO> findAllFormation() {
        List<Formation> formations = formationRepository.findAll();

        // Transformation de chaque Formation en FormationDTO
        return formations.stream()
                .map(this::maptoFomationDTO) // Appelle la méthode pour chaque Formation
                .collect(Collectors.toList()); // Collecte les DTO dans une liste
    }

    @Override
    public Formation saveFormation(Formation formation) {
        return formationRepository.save(formation);
    }

    @Override
    public FormationDTO findFormationById(Integer formationId) {
        Formation formation = formationRepository.findById(formationId).get();
        return maptoFomationDTO(formation);    }

    @Override
    public void updateFormation(Formation formation) {
        // Retrieve the existing formation
        Formation existingFormation = formationRepository.findById(formation.getId())
                .orElseThrow(() -> new RuntimeException("Formation not found"));

        // Update fields from the entity
        existingFormation.setTitle(formation.getTitle());
        existingFormation.setDescription(formation.getDescription());
        existingFormation.setStatus(formation.getStatus());

        // Save the updated formation
        formationRepository.save(existingFormation);
    }

    @Override
    public void deleteFormationById(Integer formationId) {
        formationRepository.deleteById(formationId);
    }


    private Formation maptoFomation(FormationDTO formation) {
        Formation formationDto = Formation.builder()
                .id(formation.getId())
                .title(formation.getTitle())
                .description(formation.getDescription())
                .status(formation.getStatus())
                .build();
        return formationDto;
    }


    // Méthode pour mapper une Formation en FormationDTO
    private FormationDTO maptoFomationDTO(Formation formation) {
        return FormationDTO.builder()
                .id(formation.getId())
                .description(formation.getDescription())
                .title(formation.getTitle())
                .status(formation.getStatus())
                .formateur(formation.getFormateur())
                .build();
    }
}
