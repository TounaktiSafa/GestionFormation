package com.Project.GestionsFormation.dto;

import com.Project.GestionsFormation.Entity.User;
import lombok.Builder;

import java.util.List;
@Builder
public class FormationEmplyeDTO {
    private int id;
    private String title;
    private String state;
     private List<User> employees;


    public FormationEmplyeDTO(int id,  String title,String state, List<User> employees) {
        this.id = id;
        this.state = state;
        this.title = title;
        this.employees = employees;

    }

    public List<User> getEmployees() {
        return employees;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setEmployees(List<User> employees) {
        this.employees = employees;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
