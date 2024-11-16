package com.Project.GestionsFormation.Configuration;

import com.Project.GestionsFormation.Service.CustomUserDetailServiceImplementation;
import com.Project.GestionsFormation.Service.CustumServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
public class SecuriyConfig {

    @Autowired
    private CustomUserDetailServiceImplementation customUserDetailServiceImplementation;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustumServiceHandler custumServiceHandler() {
        return new CustumServiceHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Define authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/CreateAccount", "/css/**", "/js/**").permitAll() // Public URLs
                        .requestMatchers("/admin").hasAuthority("ADMIN") // Check without ROLE_ prefix
                        .requestMatchers("/formateur").hasAuthority("FORMATEUR")
                        .requestMatchers("/employe").hasAuthority("EMPLOYE")
                        .requestMatchers("/responsablerh").hasAuthority("RESPONSABLERH")
                        .requestMatchers("/prestataire").hasAuthority("PRESTATAIRE")
                        .anyRequest().authenticated()                // All other URLs require authentication
                )

                // Configure login
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(custumServiceHandler())
                        .permitAll()
                )

                // Configure logout
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailServiceImplementation).passwordEncoder(passwordEncoder());
    }
}

