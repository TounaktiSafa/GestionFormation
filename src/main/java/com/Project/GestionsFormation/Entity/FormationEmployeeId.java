package com.Project.GestionsFormation.Entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class FormationEmployeeId implements Serializable {

    private int formationId;
    private Long employeeId;

    // Default constructor
    public FormationEmployeeId() {}

    public FormationEmployeeId(int formationId, Long employeeId) {
        this.formationId = formationId;
        this.employeeId = employeeId;
    }

    // Getters and Setters
    public int getFormationId() {
        return formationId;
    }

    public void setFormationId(int formationId) {
        this.formationId = formationId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FormationEmployeeId that = (FormationEmployeeId) o;
        return formationId == that.formationId && Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formationId, employeeId);
    }
}
