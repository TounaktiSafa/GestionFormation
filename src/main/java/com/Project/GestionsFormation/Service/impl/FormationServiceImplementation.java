package com.Project.GestionsFormation.Service.impl;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.FormationEmployeeId;
import com.Project.GestionsFormation.Entity.Formation_employees;
import com.Project.GestionsFormation.Repository.FormationEmployeesRepository;
import com.Project.GestionsFormation.Repository.FormationRepository;
import com.Project.GestionsFormation.Service.FormationService;
import com.Project.GestionsFormation.dto.FormationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.util.List;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.io.IOException;
import java.io.PrintWriter;


import java.util.List;
import java.util.stream.Collectors;
import jakarta.mail.MessagingException; // Assurez-vous que cet import est correct
@Service
public class FormationServiceImplementation implements FormationService {
    private final FormationRepository formationRepository;
    private final FormationEmployeesRepository formationEmployeesRepository;
    private Formation_employees formationEmployees;

    @Autowired
    public FormationServiceImplementation(FormationRepository formationRepository, FormationEmployeesRepository formationEmployeesRepository) {
        this.formationEmployeesRepository = formationEmployeesRepository;
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

    @Override
    public List<Formation> getFormationsByFormateur(Long formateurId) {
        return formationRepository.findByFormateurId(formateurId);
    }



    @Override
    public Formation getFormationById(Integer formationId) {
        return formationRepository.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation introuvable !"));
    }



    @Override
    public Formation getFormationById(int id) {
        return formationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
    }

@Override
    public Formation_employees getFormationEmployee(int formationId, Long employeeId) {
        FormationEmployeeId id = new FormationEmployeeId(formationId, employeeId);
        return formationEmployeesRepository.findById(id)
                .orElse(null); // Renvoie null si aucune association n'est trouvée
    }
    @Override
    public void updateProgression(int formationId, int progression) {
        System.out.println("Tentative de mise à jour de la progression pour la formation ID: " + formationId + " avec la valeur: " + progression);

        // Récupération de la formation
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        // Validation de la progression
        if (progression < 0 || progression > 100) {
            throw new IllegalArgumentException("La progression doit être entre 0 et 100.");
        }

        // Mise à jour de la progression
        formation.setProgression(progression);
        formationRepository.save(formation);

        System.out.println("Progression mise à jour avec succès !");


        if (progression == 100) {
            notifyResponsibleRh(formation.getTitle());
        }
    }
    private void notifyResponsibleRh(String formationTitle) {
        // Logique de notification (par exemple, envoi d'un email, ajout à une base de données ou système de notification)
        System.out.println("Notification RH: La formation \"" + formationTitle + "\" est achevée.");
        // Vous pouvez également intégrer un système de notification réel ici
    }






    @Override
    public List<String> getCompletedFormationTitles() {
        // Retrieve all formations
        List<Formation> formations = formationRepository.findAll();

        // Filter and return completed formations
        return formations.stream()
                .filter(formation -> formation.getProgression() == 100) // Only completed formations
                .map(Formation::getTitle) // Extract titles
                .collect(Collectors.toList());
    }




    @Override
    public List<Formation> getCompletedFormationsByEmployee(Long employeeId) {
        return formationEmployeesRepository.findByEmployeeId(employeeId)
                .stream()
                .filter(fe -> fe.getFormation().getProgression() == 100)
                .map(Formation_employees::getFormation)
                .collect(Collectors.toList());
    }
}