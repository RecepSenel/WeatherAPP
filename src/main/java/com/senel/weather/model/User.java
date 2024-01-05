package com.senel.weather.model;

import jakarta.persistence.*;

@Entity(name = "User")
@Table(name = "User")
public class User{
    @Id
    @GeneratedValue
    private String Id;
    @Column(unique = true)
    private String username;
    private String password;
    private String role;

    public String getName() {
        return username;
    }

    public void setUsername(String name, String password,String role) {
        this.username = name;
        this.password = password;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
