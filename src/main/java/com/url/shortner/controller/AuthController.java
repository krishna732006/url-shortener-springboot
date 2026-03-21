package com.url.shortner.controller;

import com.url.shortner.models.User;
import com.url.shortner.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register Page
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // Register User
    @PostMapping("/register")
    public String register(User user, Model model) {

        // Check if username already exists
        if (userRepo.findByUsername(user.getUsername()) != null) {
            model.addAttribute("error", "Username already exists!");
            return "register";
        }

        // Password length check (optional but good)
        if (user.getPassword().length() < 4) {
            model.addAttribute("error", "Password must be at least 4 characters");
            return "register";
        }

        // Encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);

        // After register → go to login
        return "redirect:/login";
    }

    // Login Page
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    // Dashboard Page
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}