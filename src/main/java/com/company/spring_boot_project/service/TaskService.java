package com.company.spring_boot_project.service;

import com.company.spring_boot_project.model.Task;
import com.company.spring_boot_project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;


}
