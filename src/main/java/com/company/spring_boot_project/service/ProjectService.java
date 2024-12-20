package com.company.spring_boot_project.service;

import com.company.spring_boot_project.model.Project;
import com.company.spring_boot_project.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // Create or update a project
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    // Get a project by ID
    public Optional<Project> getProjectById(Long id) {
        return projectRepository.findById(id);
    }

    // Get all projects
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Delete a project by ID
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

}
