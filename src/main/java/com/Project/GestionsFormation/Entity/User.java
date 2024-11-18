package com.Project.GestionsFormation.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Utilisateur",uniqueConstraints =@UniqueConstraint(columnNames = "email"))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;
    //un seul formateur -> plusierus formation
    @OneToMany(mappedBy = "formateur") // Référence à l'attribut "formateur" dans Formation
    private List<Formation> formationsAnimees;

    @ManyToMany(mappedBy = "employees") // Référence à l'attribut "employees" dans Formation
    private List<Formation> formationsInscrites;

    @Override
    public String toString() {
        return

                 lastname   +
                "  " + firstname  + "\n"

                  ;
    }

    public User() {
        super();
    }

    public User(String firstName, String lastName, String email, String password, String role) {
        super();
        this.firstname = firstName;
        this.lastname = lastName;
        this.email = email;
        this.password = password;
        this.role = role;

    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
