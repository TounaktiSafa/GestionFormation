package com.Project.GestionsFormation.Repository;

import com.Project.GestionsFormation.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Project.GestionsFormation.Entity.Formation;

import java.util.Optional;

public interface FormationRepository extends JpaRepository<Formation, Integer> {
Optional<Formation> findByTitle(String Title);
}
