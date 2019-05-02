package com.deloitte.entity;

import org.springframework.data.annotation.Id;

public class TeamMember {

    @Id
    private String id;
    private String name;
    private String role;

    public TeamMember(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public TeamMember(String id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public TeamMember(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
