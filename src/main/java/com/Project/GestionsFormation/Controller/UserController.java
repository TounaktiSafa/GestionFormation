package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Service.CustomUserDetail;
import com.Project.GestionsFormation.Service.FormationEmployeService;
import com.Project.GestionsFormation.Service.FormationService;
import com.Project.GestionsFormation.Service.UserService;
import com.Project.GestionsFormation.dto.UserDTO;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FormationService formationService;
    @Autowired
    private FormationEmployeService formationEmployeService;
    public UserController(FormationService formationService ,FormationEmployeService formationEmployeService ) {
        this.formationService = formationService;
        this.formationEmployeService = formationEmployeService;
    }
Formation formation = new Formation();
    @GetMapping("/CreateAccount")
    public String CreateAccount(@ModelAttribute("user") UserDTO userDto, Model model) {
        return "createAccount";
    }
    @PostMapping("/CreateAccount")
     public String saveUser(@ModelAttribute("user") UserDTO userDto, Model model){
           userService.save(userDto);
           model.addAttribute("message","Registred successfully");
        return "createAccount";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/dashboard")
    public String dashboard(){

        return "redirect:http://localhost:3000/dashboard/1#fullscreen";
    }







    @GetMapping("/formateur")
    public String formateur(Model model) {
        // Récupérer l'utilisateur authentifié
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail = (CustomUserDetail) authentication.getPrincipal();

        // Extraire l'ID du formateur depuis l'objet CustomUserDetail
        Long formateurId = userDetail.getId();

        // Récupérer les formations assignées au formateur
        List<Formation> formationsToAnimate = formationService.getFormationsByFormateur(formateurId);

        // Ajouter les formations au modèle
        model.addAttribute("formations", formationsToAnimate);

        return "formateur"; // Nom de la vue Thymeleaf
    }


    @GetMapping("/employe")
    public String employe(){
        return "employe";
     }




    @GetMapping("/api/completed-formations")
    @ResponseBody
    public List<Map<String, Object>> getCompletedFormations() {
        return formationEmployeService.getCompletedFormationsWithEmployees()
                .stream()
                .map(formationEmployee -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", formationEmployee.getFormation().getId());
                    map.put("title", formationEmployee.getFormation().getTitle());
                    map.put("employeeId", formationEmployee.getEmployee().getId());
                    return map;
                })
                .collect(Collectors.toList());
    }


    @GetMapping("/responsablerh")
    public String showResponsableRhPage() {
        return "responsablerh"; // Retourne la vue responsablerh.html
    }

    @GetMapping("/prestataire")
    public String prestataire(){
        return "prestataire";
    }
}
