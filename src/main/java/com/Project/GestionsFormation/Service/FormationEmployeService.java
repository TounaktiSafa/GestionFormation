package com.Project.GestionsFormation.Service;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.Formation_employees;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.dto.FormationEmplyeDTO;

import java.util.List;

public interface FormationEmployeService {

    String addEmployeeToFormation(int formationId);
    Formation getFormationById(int formationId);
    List<Formation> getAllFormationsWithDemandes();
    Formation_employees updateDemandeEmp(int formationId, Long employeeId, String newStatus);
    List<Formation> getAllFormationsWithEmployees();
    List<Formation_employees> getAllFormationEmployees();
    List<Formation_employees> getEmployeeFormations(Long employeeId);
    List<Formation_employees> getAcceptedFormationsForEmployee(Long employeeId);
    Formation_employees getFormationEmployee(int formationId, Long employeeId);
    Formation getFormationByFormateurId(int formationId, Long formateurId);
    List<Formation_employees> getCompletedFormationsWithEmployees();

}
