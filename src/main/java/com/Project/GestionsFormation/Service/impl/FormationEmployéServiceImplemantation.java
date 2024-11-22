package com.Project.GestionsFormation.Service.impl;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Repository.FormationRepository;
import com.Project.GestionsFormation.Repository.UserRepository;
import com.Project.GestionsFormation.Service.CustomUserDetail;
import com.Project.GestionsFormation.Service.FormationEmployeService;
import com.Project.GestionsFormation.dto.FormationEmplyeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FormationEmployéServiceImplemantation implements FormationEmployeService {
private    UserRepository userRepository;
private    FormationRepository formationRepository;

    public FormationEmployéServiceImplemantation(UserRepository userRepository, FormationRepository formationRepository) {
        this.userRepository = userRepository;
        this.formationRepository = formationRepository;
    }


   @Override
    public Formation getFormationById(int formationId) {
        return formationRepository.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation non trouvée pour l'ID : " + formationId));
    }
    @Override
    public String addEmployeeToFormation(int formationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetail)) {
            throw new IllegalStateException("Utilisateur non authentifié !");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        User employee = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé en base de données"));

        // Récupérer la formation
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation non trouvée pour l'ID : " + formationId));

        // Vérifier si l'utilisateur est déjà inscrit
        if (formation.getEmployees().contains(employee)) {
            throw new IllegalStateException("Vous êtes déjà inscrit à cette formation !");
        }

        // Ajouter l'employé à la formation
        formation.getEmployees().add(employee);
        formationRepository.save(formation);

        // Retourner le titre de la formation
        return formation.getTitle();
    }
    }

