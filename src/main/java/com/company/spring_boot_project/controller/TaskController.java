package com.company.spring_boot_project.controller;

import com.company.spring_boot_project.model.Task;
import com.company.spring_boot_project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping("/{projectId}")
    public String listTasks(@PathVariable Long projectId, Model model) {
        List<Task> tasks = taskService.getTasksByProjectId(projectId);
        model.addAttribute("tasks", tasks);
        model.addAttribute("projectId", projectId);
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
