package ru.kata.spring.boot_security.demo.util;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.kata.spring.boot_security.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Optional;


@Component
public class UserValidator implements Validator {

    private final UserServiceImpl usersService;

    @Autowired
    public UserValidator(UserServiceImpl personService) {
        this.usersService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Optional<User> person = usersService.getPersonByName(((User) target).getFirstName());
        if (person.isEmpty()){
            return;
        }
        errors.rejectValue("firstName","", "Человек с таким именем уже существует");
    }

}