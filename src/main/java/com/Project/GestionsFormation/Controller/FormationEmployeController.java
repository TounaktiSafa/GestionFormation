package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Service.FormationEmployeService;
import com.Project.GestionsFormation.dto.FormationEmplyeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class FormationEmployeController {
    private FormationEmployeService formationEmployeService;

    // Injection des dépendances via le constructeur
    public FormationEmployeController(FormationEmployeService formationEmployeService) {
        this.formationEmployeService = formationEmployeService;
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
}