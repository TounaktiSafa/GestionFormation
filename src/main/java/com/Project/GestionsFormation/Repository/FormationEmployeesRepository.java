package com.Project.GestionsFormation.Repository;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Entity.FormationEmployeeId;
import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Entity.Formation_employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormationEmployeesRepository extends JpaRepository<Formation_employees, FormationEmployeeId> {

    boolean existsByFormationAndEmployee(Formation formation, User employee);

    List<Formation_employees> findByFormation(Formation formation);

    List<Formation_employees> findByEmployee(User employee);
    List<Formation_employees> findByEmployeeId(Long employeeId);
    List<Formation_employees> findByEmployeeAndDemandeEmp(User employee, String demandeEmp);
    Optional<Formation_employees> findById(FormationEmployeeId id);
    List<Formation_employees> findByFormationId(int formationId); // Ajoutez cette méthode
    Optional<Formation_employees> findByEmployeeIdAndFormationId(Long employeeId, Integer formationId);

    @Query("SELECT fe.formation FROM Formation_employees fe WHERE fe.employee.id = :employeeId")
    List<Formation> findFormationsByEmployeeId(@Param("employeeId") Long employeeId);

}
