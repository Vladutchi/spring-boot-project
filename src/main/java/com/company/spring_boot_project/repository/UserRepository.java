package com.company.spring_boot_project.repository;

import com.company.spring_boot_project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
