package com.Project.GestionsFormation.Service;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.dto.FormationEmplyeDTO;

import java.util.List;

public interface FormationEmployeService {

    String addEmployeeToFormation(int formationId);
    Formation getFormationById(int formationId);
}
