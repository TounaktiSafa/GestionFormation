package com.Project.GestionsFormation.Service;

import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.dto.UserDTO;
import org.springframework.context.annotation.Bean;

public interface UserService {

    User save(UserDTO userDTO);
}
