package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserValidator userValidator;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserValidator userValidator, RoleRepository roleRepository, UserService userService) {
        this.userValidator = userValidator;
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
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "show";
        } else {
            return "errorPage";
        }
    }



    @GetMapping(value = "/new")
    public String createUser(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleRepository.findAll());
        return "new";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @RequestParam("selectedRole") String selectedRole) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "new";
        }
        Role role = new Role(selectedRole);
        role.setUser(user);
        user.getRoles().add(role);
        userService.addNewPerson(user);
        return "redirect:/admin";
    }




    @GetMapping("/{id}/edit")
    public String showEditUserForm(@PathVariable("id") int id, ModelMap model) {
        Optional<User> userOptional = userService.getPersonById(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "edit";
        } else {
            return "errorPage";
        }
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
}

