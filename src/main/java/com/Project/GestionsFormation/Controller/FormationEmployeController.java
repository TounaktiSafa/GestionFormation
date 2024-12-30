package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.Formation_employees;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Service.CustomUserDetail;
import com.Project.GestionsFormation.Service.FormationEmployeService;
import com.Project.GestionsFormation.Service.FormationService;
import com.Project.GestionsFormation.dto.FormationEmplyeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class FormationEmployeController {
    private static final Logger logger = LoggerFactory.getLogger(FormationEmployeController.class);

    private FormationEmployeService formationEmployeService;
    private FormationService formationService;

    // Injection des dépendances via le constructeur
    public FormationEmployeController(FormationEmployeService formationEmployeService, FormationService formationService) {
        this.formationEmployeService = formationEmployeService;
        this.formationService = formationService;
    }

    @GetMapping("/inscrire/{formationId}")
    public String afficherFormulaireInscription(@PathVariable int formationId, Model model) {
        // Récupérer la formation par son ID depuis la base de données
        Formation formation = formationEmployeService.getFormationById(formationId);  // Utilisez une méthode de votre service qui récupère la formation par ID

        // Vérifier si la formation existe, sinon envoyer une erreur
        if (formation == null) {
            model.addAttribute("error", "Formation non trouvée.");
            return "error";  // Affichez une page d'erreur
        }

        // Ajouter la formation et un nouvel employé au modèle
        model.addAttribute("formation", formation);  // Assurez-vous que "formation" est bien un objet contenant le titre
        model.addAttribute("employee", new User());  // Un objet employé vide pour lier le formulaire
        return "sinscrireFormation";  // Retourne la page du formulaire
    }

    @PostMapping("/inscrire/{formationId}")
    public String inscrireEmploye(@PathVariable Integer formationId, @ModelAttribute("employee") User employee, Model model, RedirectAttributes redirectAttributes) {
        try {
            // Appelle le service pour ajouter l'utilisateur connecté à la formation
            String formationTitle = formationEmployeService.addEmployeeToFormation(formationId);
            //model.addAttribute("message", "Inscription réussie à la formation : " + formationTitle);

            redirectAttributes.addFlashAttribute("message", "Vous êtes inscrit à la formation : " + formationTitle);

            return "redirect:/inscrire/" + formationId;  // Redirection vers une page de succès
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Affichez une erreur
        }
    }











    @GetMapping("/demande-formation")
    public String getFormationEmployees(Model model) {
        List<Formation_employees> formationEmployees = formationEmployeService.getAllFormationEmployees();
        model.addAttribute("formationEmployees", formationEmployees);

        // Ajouter des messages si nécessaire
        model.addAttribute("message", "Voici vos demandes de formation.");
        // Ou pour les erreurs
         model.addAttribute("error", "Une erreur est survenue.");

        return "demandeFormation"; // Thymeleaf template name
    }


    /**
     * Update the demandeEmp attribute for a specific formation-employee pair.
     */
    @PostMapping("/demande-formation/update")
    public String updateDemandeEmp(
            @RequestParam int formationId,
            @RequestParam Long employeeId,
            @RequestParam String newStatus,
            RedirectAttributes redirectAttributes) {
        try {
            formationEmployeService.updateDemandeEmp(formationId, employeeId, newStatus);
            redirectAttributes.addFlashAttribute("message", "Status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error updating status: " + e.getMessage());
        }
        return "redirect:/demande-formation"; // Redirection vers la liste des demandes
    }

    @GetMapping("/employee/formations")
    public String getEmployeeFormations(Model model) {
        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetail)) {
            model.addAttribute("error", "Utilisateur non authentifié !");
            return "error"; // Page d'erreur
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        // Récupérer les formations de l'utilisateur
        List<Formation_employees> formationEmployees = formationEmployeService.getEmployeeFormations(userDetail.getId());
        model.addAttribute("formationEmployees", formationEmployees);

        return "employeeFormations"; // Page Thymeleaf pour l'affichage
    }
    @GetMapping("/formations-acceptes")
    public String showAcceptedFormations(Model model) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetail)) {
            throw new IllegalStateException("Utilisateur non authentifié !");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        Long employeeId = userDetail.getId(); // ID de l'utilisateur connecté

        // Récupérer les formations acceptées
        List<Formation_employees> acceptedFormations = formationEmployeService.getAcceptedFormationsForEmployee(employeeId);
        model.addAttribute("acceptedFormations", acceptedFormations);
        return "formationsAcceptes";
    }
    /*
    @GetMapping("/formation/espace/{formationId}")
    public String accessFormationSpace(
            @PathVariable int formationId,
            Model model) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetail)) {
            throw new IllegalStateException("Utilisateur non authentifié !");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        Long employeeId = userDetail.getId();

        // Vérifier si l'employé a accès à la formation
        Formation_employees formationEmployee = formationEmployeService.getFormationEmployee(formationId, employeeId);

        if (formationEmployee == null || !"Accepted".equals(formationEmployee.getDemandeEmp())) {
            throw new IllegalStateException("Accès refusé : Formation non acceptée ou inexistante !");
        }

        // Charger les informations de l'espace de formation
        model.addAttribute("formation", formationEmployee.getFormation());
        model.addAttribute("employee", formationEmployee.getEmployee());
       // Si progression ajoutée

        return "espaceFormation"; // Nom de la page Thymeleaf pour l'espace de formation
    }






    @GetMapping("/formation/espace/{formationId}")
    public String accessFormationSpace(
            @PathVariable int formationId,
            Model model) {
        // Récupérer l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetail)) {
            throw new IllegalStateException("Utilisateur non authentifié !");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetail.getId(); // ID de l'utilisateur connecté

        // Récupérer le rôle de l'utilisateur
        boolean isFormateur = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_FORMATEUR"));

        try {
            if (isFormateur) {
                // Si utilisateur est formateur, vérifier qu'il anime cette formation
                Formation formation = formationEmployeService.getFormationById(formationId);
                model.addAttribute("formation", formation);
                model.addAttribute("userRole", "FORMATEUR");
            } else {
                // Si utilisateur est employé, vérifier qu'il a accès à cette formation
                Formation_employees formationEmployee = formationEmployeService.getFormationEmployee(formationId, userId);

                if (formationEmployee == null || !"Accepted".equals(formationEmployee.getDemandeEmp())) {
                    throw new IllegalStateException("Accès refusé : Formation non acceptée ou inexistante !");
                }

                model.addAttribute("formation", formationEmployee.getFormation());
                model.addAttribute("userRole", "employé");
            }

            return "espaceFormation"; // Vue commune pour employés et formateurs
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error"; // Page d'erreur
        }
    }
    */

    @GetMapping("/formation/espace/{formationId}")
    public String accessFormationSpace(
            @PathVariable int formationId,
            Model model) {
        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication.getPrincipal() instanceof CustomUserDetail)) {
            throw new IllegalStateException("Utilisateur non authentifié !");
        }

        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        Long userId = userDetail.getId();

        // Vérifier le rôle de l'utilisateur
        boolean isFormateur = authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("FORMATEUR"));

        if (isFormateur) {
            // Logique pour le formateur
            Formation formation = formationEmployeService.getFormationByFormateurId(formationId, userId);
            if (formation == null) {
                throw new IllegalStateException("Vous n'êtes pas autorisé à animer cette formation !");
            }

            // Ajouter les données de formation au modèle
            model.addAttribute("formationTitle", formation.getTitle());
            return "espaceFormation"; // Page spécifique pour le formateur
        } else {
            // Logique pour l'employé
            Formation_employees formationEmployee = formationEmployeService.getFormationEmployee(formationId, userId);
            if (formationEmployee == null || !"Accepted".equals(formationEmployee.getDemandeEmp())) {
                throw new IllegalStateException("Accès refusé : Formation non acceptée ou inexistante !");
            }

            // Ajouter les données de formation au modèle
            model.addAttribute("formationTitle", formationEmployee.getFormation().getTitle());
            return "espaceFormation"; // Page spécifique pour l'employé
        }
    }


    @PostMapping("/formation/progression/{formationId}")
    public String updateProgression(@PathVariable int formationId, @RequestParam int progression, RedirectAttributes redirectAttributes) {
        try {
            formationService.updateProgression(formationId, progression);
            redirectAttributes.addFlashAttribute("message", "Progression mise à jour avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la mise à jour de la progression : " + e.getMessage());
        }
        return "redirect:/formateur";
    }

















}