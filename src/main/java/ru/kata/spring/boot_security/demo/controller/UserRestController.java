package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
@RequestMapping("/api/user")
public class UserRestController {

    @GetMapping
    public ResponseEntity<User> getAUthUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
