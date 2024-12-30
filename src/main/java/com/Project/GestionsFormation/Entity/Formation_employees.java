package com.Project.GestionsFormation.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "formation_employees")
public class Formation_employees {

    @EmbeddedId
    private FormationEmployeeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("formationId")
    private Formation formation;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    private User employee;

    @Column(name = "demande_emp", nullable = true)
    private String demandeEmp;

    // Default constructor
    public Formation_employees() {}

    // Constructor
    public Formation_employees(Formation formation, User employee, String demandeEmp) {
        this.formation = formation;
        this.employee = employee;
        this.demandeEmp = demandeEmp;
        this.id = new FormationEmployeeId(formation.getId(), employee.getId());
    }

    // Getters and setters
    public FormationEmployeeId getId() {
        return id;
    }

    public void setId(FormationEmployeeId id) {
        this.id = id;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }

    public String getDemandeEmp() {
        return demandeEmp;
    }

    public void setDemandeEmp(String demandeEmp) {
        this.demandeEmp = demandeEmp;
    }
}
