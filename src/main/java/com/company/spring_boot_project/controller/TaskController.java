package com.company.spring_boot_project.controller;

import com.company.spring_boot_project.model.Project;
import com.company.spring_boot_project.model.Task;
import com.company.spring_boot_project.model.User;
import com.company.spring_boot_project.service.ProjectService;
import com.company.spring_boot_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProjectService projectService;

    @GetMapping("/{projectId}")
    public String listTasks(@PathVariable Long projectId, Model model, Principal principal) {
        // Fetch the project details
        Project project = projectService.getProjectById(projectId);

        // Fetch tasks for the project
        List<Task> tasks = taskService.getTasksByProjectId(projectId);

        // Determine if the logged-in user is the owner of the project
        String loggedInUserEmail = principal.getName();
        User owner = project.getOwner(); // Fetch the owner of the project
        boolean isOwner = owner.getEmail().equals(loggedInUserEmail); // Verify ownership

        // Get collaborators (list already excludes the owner)
        List<User> collaborators = project.getCollaborators();

        // Add project, tasks, ownership information, and collaborators to the model
        model.addAttribute("project", project);
        model.addAttribute("tasks", tasks);
        model.addAttribute("owner", owner); // Add the owner object
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("collaborators", collaborators);

        return "task-list";
    }



    @GetMapping("/{projectId}/new")
    public String showCreateTaskForm(@PathVariable Long projectId, Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("projectId", projectId);
        return "task-form";
    }

    @PostMapping("/{projectId}")
    public String createTask(@PathVariable Long projectId, @ModelAttribute Task task) {
        taskService.createTask(projectId, task);
        return "redirect:/tasks/" + projectId;
    }

    @GetMapping("/{projectId}/edit/{id}")
    public String showEditTaskForm(@PathVariable Long projectId, @PathVariable Long id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("projectId", projectId);
        return "task-form";
    }


    @PostMapping("/{projectId}/{id}")
    public String updateTask(@PathVariable Long projectId, @PathVariable Long id, @ModelAttribute Task task) {
        taskService.updateTask(id, task);
        return "redirect:/tasks/" + projectId;
    }

    @PostMapping("/{projectId}/delete/{id}")
    public String deleteTask(@PathVariable Long projectId, @PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/tasks/" + projectId;
    }
}
