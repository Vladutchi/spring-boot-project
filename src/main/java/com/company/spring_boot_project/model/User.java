package com.company.spring_boot_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    // Shared projects: Projects the user collaborates on but does not own
    @ManyToMany(mappedBy = "users")
    private List<Project> sharedProjects = new ArrayList<>();

    // Owned projects: Projects created by the user
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> ownedProjects = new ArrayList<>();

    @Transient
    private String confirmPassword;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Project> getSharedProjects() {
        return sharedProjects;
    }

    public void setSharedProjects(List<Project> sharedProjects) {
        this.sharedProjects = sharedProjects;
    }

    public List<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(List<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
