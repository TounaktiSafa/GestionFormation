package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Service.CustomUserDetail;
import com.Project.GestionsFormation.Service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeDashboardController {

    @Autowired
    private FormationService formationService;

    @GetMapping("/employee/completed-courses")
    public String getCompletedCoursesForEmployee(Model model) {
        // Récupérer l'ID de l'employé connecté
        Long employeeId = getCurrentEmployeeId();

        // Récupérer les formations complétées pour cet employé
        List<Formation> completedCourses = formationService.getCompletedFormationsByEmployee(employeeId);

        // Ajouter les données au modèle
        model.addAttribute("completedCourses", completedCourses);
        model.addAttribute("employeeId", employeeId);

        return "employee-dashboard"; // Nom de votre vue HTML
    }

    private Long getCurrentEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();
        return userDetail.getId();
    }
}
