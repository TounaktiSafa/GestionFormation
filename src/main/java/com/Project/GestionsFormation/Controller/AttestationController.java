package com.Project.GestionsFormation.Controller;

import com.Project.GestionsFormation.Entity.Formation;
import com.Project.GestionsFormation.Repository.FormationEmployeesRepository;
import com.Project.GestionsFormation.Service.AttestationService;
import com.Project.GestionsFormation.Service.CustomUserDetail;
import com.Project.GestionsFormation.Service.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
public class AttestationController {

}
