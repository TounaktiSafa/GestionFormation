package com.Project.GestionsFormation.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormationController {
    @GetMapping("/")
    public String home() {
     return "homePage";
    }

}
