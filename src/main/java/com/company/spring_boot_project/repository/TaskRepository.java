package com.company.spring_boot_project.repository;

import com.company.spring_boot_project.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
