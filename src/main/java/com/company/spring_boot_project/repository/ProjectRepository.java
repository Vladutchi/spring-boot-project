package com.company.spring_boot_project.repository;

import com.company.spring_boot_project.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
