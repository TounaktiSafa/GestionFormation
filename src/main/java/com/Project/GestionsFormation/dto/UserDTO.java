package com.Project.GestionsFormation.dto;

public class UserDTO {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String role;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserDTO(String lastname, String email, String password, String role, String firstname) {
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.firstname = firstname;
    }
}
