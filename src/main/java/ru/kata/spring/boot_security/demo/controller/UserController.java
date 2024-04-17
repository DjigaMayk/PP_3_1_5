package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {
    private UserService userService;
    private UsersRepository userRepository;

    @Autowired
    public UserController(UserService userService, UsersRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = userRepository.findByFirstName(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: "));
        model.addAttribute("user", user);
        return "user";
    }


}