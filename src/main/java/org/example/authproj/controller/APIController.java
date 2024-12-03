package org.example.authproj.controller;

import org.example.authproj.Service.UserService;
import org.example.authproj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class APIController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public APIController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user){
        userService.registerNewUser(user);
        return "redirect:/login";
    }
    @GetMapping
    public String index(Model model){
        model.addAttribute("user", new User());
        return "index";
    }
}
