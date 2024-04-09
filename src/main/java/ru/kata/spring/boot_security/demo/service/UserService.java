package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    void addNewPerson(User user);
    Optional<User> getPersonById(int id);
    Optional<User> getPersonByName(String name);
    List<User> getAllPersons();
    void changePersonById(int id, User user);
    void deletePersonById(int id);
    void changePassword(int userId, String newPassword);


}
