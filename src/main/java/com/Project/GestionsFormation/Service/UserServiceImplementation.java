package com.Project.GestionsFormation.Service;

import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Repository.UserRepository;
import com.Project.GestionsFormation.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
   private UserRepository userRepository;
    @Override

    public User save(UserDTO userDTO) {
        // Do not pass id explicitly as it's auto-generated
        User user = new User(
                userDTO.getFirstname(),
                userDTO.getLastname(),
                userDTO.getEmail(),
                passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getRole()
        );
        return userRepository.save(user);
    }

}
