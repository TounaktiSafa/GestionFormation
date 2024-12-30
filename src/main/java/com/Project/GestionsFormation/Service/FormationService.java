package com.Project.GestionsFormation.Service;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.Formation_employees;
import com.Project.GestionsFormation.dto.FormationDTO;
import java.io.ByteArrayOutputStream;

import java.util.List;

public interface FormationService {

    List<FormationDTO> findAllFormation();
Formation saveFormation(Formation formation);

    FormationDTO findFormationById(Integer formationId);

    public List<Formation> getFormationsByFormateur(Long formateurId);
    void updateFormation(Formation formation);
    void deleteFormationById(Integer formationId);
    Formation getFormationById(Integer formationId);
    public Formation getFormationById(int id);
    Formation_employees getFormationEmployee(int formationId, Long employeeId);
    void updateProgression(int formationId, int progression);
    List<String> getCompletedFormationTitles();
    List<Formation> getCompletedFormationsByEmployee(Long employeeId);

}
