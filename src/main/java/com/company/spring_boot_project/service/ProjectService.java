package com.company.spring_boot_project.service;

import com.company.spring_boot_project.model.Project;
import com.company.spring_boot_project.model.User;
import com.company.spring_boot_project.repository.ProjectRepository;
import com.company.spring_boot_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Project> getProjectsByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getOwnedProjects();
    }

    public List<Project> getSharedProjectsByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getSharedProjects();
    }

    public void createProject(Project project, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        project.setOwner(user); // Set the owner
        projectRepository.save(project); // Save the project
    }


    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
    }

    public void updateProject(Long id, Project updatedProject) {
        Project project = getProjectById(id);
        project.setTitle(updatedProject.getTitle());
        project.setDescription(updatedProject.getDescription());
        project.setDueDate(updatedProject.getDueDate());
        projectRepository.save(project);
    }

    public void addCollaborator(Long projectId, String userEmail) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));

        if (project.getOwner().equals(user)) {
            throw new IllegalArgumentException("Owner cannot be a collaborator.");
        }

        if (project.getUsers().contains(user)) {
            throw new IllegalArgumentException("User is already a collaborator.");
        }

        project.getUsers().add(user); // Add user as a collaborator
        projectRepository.save(project); // Save changes to the project
    }



    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}
