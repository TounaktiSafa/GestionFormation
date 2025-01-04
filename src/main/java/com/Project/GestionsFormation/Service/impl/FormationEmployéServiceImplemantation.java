package com.Project.GestionsFormation.Service.impl;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.FormationEmployeeId;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Entity.Formation_employees;
import com.Project.GestionsFormation.Repository.FormationEmployeesRepository;
import com.Project.GestionsFormation.Repository.FormationRepository;
import com.Project.GestionsFormation.Repository.UserRepository;
import com.Project.GestionsFormation.Service.CustomUserDetail;
import com.Project.GestionsFormation.Service.FormationEmployeService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormationEmployéServiceImplemantation implements FormationEmployeService {
    private final UserRepository userRepository;
    private final FormationRepository formationRepository;
    private final FormationEmployeesRepository formationEmployeesRepository; // Add this repository

    public FormationEmployéServiceImplemantation(UserRepository userRepository,
                                                 FormationRepository formationRepository,
                                                 FormationEmployeesRepository formationEmployeesRepository) {
        this.userRepository = userRepository;
        this.formationRepository = formationRepository;
        this.formationEmployeesRepository = formationEmployeesRepository;
    }

    @Override
    public Formation getFormationById(int formationId) {
        return formationRepository.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation non trouvée pour l'ID : " + formationId));
    }




    public List<Formation> getAllFormationsWithDemandes() {
        return formationRepository.findAll();
    }

    // Method to update the demandeEmp status (Accepted or Rejected)





    @Override

    public String addEmployeeToFormation(int formationId) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetail)) {
            throw new IllegalStateException("Utilisateur non authentifié !");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        User employee = userRepository.findById(userDetail.getId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé en base de données"));

        // Retrieve the formation
        Formation formation = formationRepository.findById(formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation non trouvée pour l'ID : " + formationId));

        // Check if the user is already associated with the formation
        boolean alreadyEnrolled = formationEmployeesRepository.existsByFormationAndEmployee(formation, employee);
        if (alreadyEnrolled) {
            throw new IllegalStateException("Vous êtes déjà inscrit à cette formation !");
        }

        // Create and save the new association in the formation_employees table
        Formation_employees formationEmployee = new Formation_employees(formation, employee, "Pending");
        formationEmployeesRepository.save(formationEmployee);

        // Return the title of the formation
        return formation.getTitle();
    }
















    public List<Formation> getAllFormationsWithEmployees() {
        return formationRepository.findAll(); // Fetches all formations with associated employees.
    }
    public List<Formation_employees> getAllFormationEmployees() {
        return formationEmployeesRepository.findAll(); // Fetch all formation-employee records
    }

    /**
     * Update the demandeEmp attribute for a specific formation-employee pair.
     */
    @Override
    public Formation_employees updateDemandeEmp(int formationId, Long employeeId, String newStatus) {
        FormationEmployeeId id = new FormationEmployeeId(formationId, employeeId);

        // Fetch the existing formation-employee record
        Formation_employees formationEmployee = formationEmployeesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No formation-employee association found for Formation ID: " + formationId +
                                " and Employee ID: " + employeeId));

        // Update the demandeEmp status
        formationEmployee.setDemandeEmp(newStatus);

        // Save the updated entity back to the database
        return formationEmployeesRepository.save(formationEmployee);
    }

    @Override
    public List<Formation_employees> getEmployeeFormations(Long employeeId) {
        return formationEmployeesRepository.findByEmployeeId(employeeId);
    }
    public List<Formation_employees> getAcceptedFormationsForEmployee(Long employeeId) {
        return formationEmployeesRepository.findByEmployeeAndDemandeEmp(
                userRepository.findById(employeeId)
                        .orElseThrow(() -> new IllegalArgumentException("Employee not found")),
                "Accepted"
        );
    }
    @Override
    public Formation_employees getFormationEmployee(int formationId, Long employeeId) {
        FormationEmployeeId id = new FormationEmployeeId(formationId, employeeId);
        return formationEmployeesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aucune association trouvée pour cette formation et cet employé."));
    }


    public Formation getFormationByFormateurId(int formationId, Long formateurId) {
        return formationRepository.findById(formationId)
                .filter(formation -> formation.getFormateur() != null && formateurId.equals(formation.getFormateur().getId()))
                .orElseThrow(() -> new IllegalArgumentException("Vous n'êtes pas autorisé à animer cette formation !"));
    }
    @Override
    public List<Formation_employees> getCompletedFormationsWithEmployees() {
        // Récupérer toutes les formations terminées
        return formationEmployeesRepository.findAll()
                .stream()
                .filter(fe -> fe.getFormation().getProgression() == 100) // Vérifie si la formation est terminée
                .collect(Collectors.toList());
    }


    @Override
    public Formation_employees findByEmployeeIdAndFormationId(Long employeeId, Integer formationId) {
        return formationEmployeesRepository
                .findByEmployeeIdAndFormationId(employeeId, formationId)
                .orElseThrow(() -> new IllegalArgumentException("Formation not found or not assigned to the employee."));
    }



@Override
    public void saveEvaluation(Formation_employees formationEmployee, int rating) {
        if (formationEmployee != null) {
            formationEmployee.setEvaluation(rating); // Set the evaluation
            formationEmployeesRepository.save(formationEmployee); // Save to the database
        } else {
            throw new RuntimeException("FormationEmployees record not found.");
        }
    }
@Override
    public void save(Formation_employees formationEmployee) {
        formationEmployeesRepository.save(formationEmployee);
    }


}

