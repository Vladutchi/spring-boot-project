package com.company.spring_boot_project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Enable CSRF protection
                .csrf(csrf -> csrf
                        .csrfTokenRepository(org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                // Allow access to static resources
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/stativ/js/**", "/images/**").permitAll() // Static resources
                        .requestMatchers("/register", "/login", "/login-error").permitAll() // Public pages
                        .anyRequest().authenticated() // All other requests require authentication
                )
                // Configure login
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .failureUrl("/login-error?error=true") // Redirect to login error page
                        .defaultSuccessUrl("/projects", true) // Redirect to homepage after successful login
                        .permitAll()
                )
                // Configure logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout=true") // Redirect after logout
                        .invalidateHttpSession(true) // Invalidate session
                        .deleteCookies("JSESSIONID") // Delete session cookie
                        .permitAll()
                )
                // Set security headers manually
                .headers(headers -> headers
                        .addHeaderWriter(new CustomSecurityHeaderWriter())
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    // Custom HeaderWriter for setting security headers manually
    private static class CustomSecurityHeaderWriter implements HeaderWriter {
        @Override
        public void writeHeaders(HttpServletRequest request, HttpServletResponse response) {
            response.addHeader("Content-Security-Policy", "default-src 'self'; script-src 'self'; style-src 'self'; img-src 'self' data:;");
            response.addHeader("Strict-Transport-Security", "max-age=31536000; includeSubDomains");
            response.addHeader("Permissions-Policy", "geolocation=(self), microphone=()");
            response.addHeader("X-Content-Type-Options", "nosniff");
        }
    }
}
