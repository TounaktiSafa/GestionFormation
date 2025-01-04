package com.Project.GestionsFormation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Controller
public class FormationController {


    @GetMapping("/")
    public String home() {
        return "homePage";
    }

    @GetMapping("/meet")
    public String meet() {

        return "videocall";
    }

    @GetMapping("/animerFormation")
    public String animerFormation() {

        return "espaceFormation";
    }



}