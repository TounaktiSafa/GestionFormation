package com.Project.GestionsFormation.Service;

import com.Project.GestionsFormation.Entity.User;
import com.Project.GestionsFormation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailServiceImplementation implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
       if (user == null) {
           throw new UsernameNotFoundException(username);
       }
        return new CustomUserDetail(user);
    }
}
