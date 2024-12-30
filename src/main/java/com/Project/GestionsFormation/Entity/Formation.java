package com.Project.GestionsFormation.Entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "Formation")
@Builder
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Utilisation d'IDENTITY pour la génération auto-incrémentée
    private int id;

    private String title;
    private String description;
    private String status;

    private int evaluate;
    private int progression;
    // Relation plusieurs formations -> un seul formateur
    @ManyToOne
    @JoinColumn(name = "formateur_id", nullable = false) // Clé étrangère vers l'utilisateur formateur
    private User formateur;

    // Relation plusieurs employés -> plusieurs formations
    @ManyToMany
    @JoinTable(
            name = "formation_employees",
            joinColumns = @JoinColumn(name = "formation_id"), // Clé de Formation
            inverseJoinColumns = @JoinColumn(name = "employee_id") // Clé de User
    )
    private List<User> employees;

    // Constructeur sans argument requis par JPA
    public Formation() {
    }

    // Constructeur avec arguments
    public Formation(int id, String title, String description, String status,int evaluate,int progression, User formateur, List<User> employees) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.formateur = formateur;
        this.employees = employees;
        this.evaluate = evaluate;
        this.progression = progression;

    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getFormateur() {
        return formateur;
    }

    public void setFormateur(User formateur) {
        this.formateur = formateur;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }
    public int getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(int evaluate) {}

    public int getProgression() {
        return progression;
    }

    public void setProgression(int progression) {
        this.progression = progression;
    }
}