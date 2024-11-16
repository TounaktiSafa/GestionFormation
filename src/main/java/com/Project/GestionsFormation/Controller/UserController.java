package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Service.UserService;
import com.Project.GestionsFormation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
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
        return "dashboard";
    }
     @GetMapping("/formateur")
    public String formateur(){
return "formateur";
     }
     @GetMapping("/employe")
    public String employe(){
        return "employe";
     }
    @GetMapping("/responsablerh")
    public String responsablerh() {
        return "responsablerh";
    }

    @GetMapping("/prestataire")
    public String prestataire(){
        return "prestataire";
    }
}
