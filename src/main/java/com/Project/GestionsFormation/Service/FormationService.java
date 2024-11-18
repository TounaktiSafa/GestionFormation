package com.Project.GestionsFormation.Service;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.dto.FormationDTO;

import java.util.List;

public interface FormationService {
    List<FormationDTO> findAllFormation();
Formation saveFormation(Formation formation);

    FormationDTO findFormationById(Integer formationId);


    void updateFormation(Formation formation);
    void deleteFormationById(Integer formationId);
}
