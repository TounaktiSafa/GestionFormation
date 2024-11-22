package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Service.FormationService;
import com.Project.GestionsFormation.dto.FormationDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GestionFormationController {
    private FormationService formationService;

    public GestionFormationController(FormationService formationService) {
        this.formationService = formationService;
    }


    @GetMapping("/GestionFormation")
public String listFormation(Model model) {
        List< FormationDTO> formation = formationService.findAllFormation();
   model.addAttribute("formation", formation);

    return "GestionFormation";

    }






    @GetMapping("/CreerFormation")
  public String CréerFormation(Model model) {
        Formation formation = new Formation();
model.addAttribute("formation", formation);
        return "creerFormation";
    }
@PostMapping("/CreerFormation")
public String SaveFormation(@ModelAttribute("formation") Formation formation){
        formationService.saveFormation(formation);
        return "redirect:/GestionFormation";
}
@GetMapping("/updateFormation/{formationId}")
    public String updateFormation(@PathVariable("formationId") Integer formationId,Model model) {
        FormationDTO formationDTO = formationService.findFormationById((formationId));
        model.addAttribute("formation", formationDTO);
        return "updateFormation";

}
    @PostMapping("/updateFormation/{formationId}")
    public String UpdateFormation(@PathVariable("formationId") int formationId, @ModelAttribute("formation") FormationDTO formationDTO) {
        // Convert FormationDTO to Formation entity
        Formation formation = new Formation();
        formation.setId(formationId);
        formation.setTitle(formationDTO.getTitle());
        formation.setDescription(formationDTO.getDescription());
        formation.setStatus(formationDTO.getStatus());

        // Pass the Formation entity to the service
        formationService.updateFormation(formation);

        return "redirect:/GestionFormation"; // Redirect after update
    }

    @GetMapping("/deleteFormation/{formationId}")
        public String deleteFormation(@PathVariable("formationId") int formationId){
            formationService.deleteFormationById(formationId);
            return "redirect:/GestionFormation";
        }



}
