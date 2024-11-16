package com.Project.GestionsFormation.Service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustumServiceHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        var authorities = authentication.getAuthorities();
        var role = authorities.stream().map(r -> r.getAuthority()).findFirst().orElse("");

        // Ensure only one redirect happens
        if (role.equals("ADMIN")) {
            response.sendRedirect("/dashboard");
        } else if (role.equals("FORMATEUR")) {
            response.sendRedirect("/formateur");
        } else if (role.equals("EMPLOYE")) {
            response.sendRedirect("/employe");
        } else if (role.equals("RESPONSABLERH")) {
            response.sendRedirect("/responsablerh");
        } else if (role.equals("PRESTATAIRE")) {
            response.sendRedirect("/prestataire");
        } else {
            response.sendRedirect("/");
        }
    }
}
