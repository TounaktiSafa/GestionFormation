package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Service.FormationService;
import com.Project.GestionsFormation.dto.FormationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {
    private final FormationService formationService;

    public EmployeeController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping("/employeformation")
    public String listFormation(Model model) {
        List<FormationDTO> formations = formationService.findAllFormation();
        model.addAttribute("formation", formations);
        return "AffchageFormationEmploye"; // Assurez-vous que ce nom correspond à votre fichier employe.html
    }
}