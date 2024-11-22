package com.Project.GestionsFormation.Repository;

import com.Project.GestionsFormation.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
User findByEmail(String email);
User findById(long id);
}
