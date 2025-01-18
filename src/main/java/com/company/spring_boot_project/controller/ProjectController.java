package com.company.spring_boot_project.controller;

import com.company.spring_boot_project.model.Project;
import com.company.spring_boot_project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public String listProjects(Model model, Principal principal) {
        String email = principal.getName(); // Get logged-in user's email
        model.addAttribute("projects", projectService.getProjectsByUserEmail(email)); // Owned projects
        model.addAttribute("sharedProjects", projectService.getSharedProjectsByUserEmail(email)); // Shared projects
        return "project-list";
    }

    @GetMapping("/new")
    public String showCreateProjectForm(Model model) {
        model.addAttribute("project", new Project());
        return "project-form";
    }

    @PostMapping
    public String createProject(@ModelAttribute Project project, Principal principal) {
        projectService.createProject(project, principal.getName());
        return "redirect:/projects";
    }

    @GetMapping("/{id}/edit")
    public String showEditProjectForm(@PathVariable Long id, Model model) {
        model.addAttribute("project", projectService.getProjectById(id));
        return "project-form";
    }

    @PostMapping("/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute Project project) {
        projectService.updateProject(id, project);
        return "redirect:/projects";
    }

    @PostMapping("/{projectId}/collaborators")
    public String addCollaborator(
            @PathVariable Long projectId,
            @RequestParam String userEmail,
            @RequestParam ProjectService.CollaboratorAction action,
            RedirectAttributes redirectAttributes) {
        try {
            projectService.manageCollaborator(projectId, userEmail, action);

            // Set dynamic success message based on the action
            String successMessage = (action == ProjectService.CollaboratorAction.ADD)
                    ? "Collaborator added successfully."
                    : "Collaborator removed successfully.";
            redirectAttributes.addFlashAttribute("successMessage", successMessage);

        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/tasks/" + projectId; // Redirect back to the project tasks page
    }



    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}
