package com.Project.GestionsFormation.dto;

import com.Project.GestionsFormation.Entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;

@Builder
public class FormationDTO {
    private int id;

    private String title;

    public User getFormateur() {
        return formateur;
    }

    public void setFormateur(User formateur) {
        this.formateur = formateur;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;
    private String status;

    // plusieurs formations -> un seul formateur
   // Liaison avec User pour le formateur
    private User formateur;
}
