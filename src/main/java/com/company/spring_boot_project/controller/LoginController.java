package com.company.spring_boot_project.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // This maps to the login.html page
    }
    
    @GetMapping("/login-error")
    public String loginError(@RequestParam(value = "error", required = false) String error, HttpServletRequest request) {
        if (error != null) {
            request.setAttribute("errorMessage", "Invalid username or password.");
        }
        return "login";
    }

}

