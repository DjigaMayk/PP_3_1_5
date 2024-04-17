package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public AdminController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping
    public String showAllUsers(ModelMap model) {
        List<User> users = userService.getAllPersons();
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") int id, ModelMap model) {
        Optional<User> userOptional = userService.getPersonById(id);
        model.addAttribute("user", userOptional.get());
        return "show";

    }



    @GetMapping(value = "/new")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute(value = "user") User user) {
        userService.addNewPerson(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "/{id}/change-password")
    public String changePasswordForm(ModelMap model, @PathVariable("id") int id) {
        User user = userService.getPersonById(id).get();
        model.addAttribute("user", user);
        return "changePassword";
    }

    @PostMapping(value = "/{id}/change-password")
    public String changePassword(
            @ModelAttribute("user") User user,
            @PathVariable("id") int id,
            @RequestParam("password") String password,
            @RequestParam("confirmPassword") String confirmPassword
    ) {
        if (!password.equals(confirmPassword)) {
            return "redirect:/admin/" + id + "/change-password?error=passwordMismatch";
        }
        userService.changePassword(id, password);
        return "redirect:/admin";
    }




    @GetMapping("/{id}/edit")
    public String showEditUserForm(@PathVariable("id") int id, ModelMap model) {
        Optional<User> userOptional = userService.getPersonById(id);
        model.addAttribute("user", userOptional.get());
        return "edit";
        }



    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.changePersonById(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deletePersonById(id);
        return "redirect:/admin";
    }
}

