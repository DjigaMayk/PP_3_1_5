package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UsersService {

    List<User> findAll();

    User findOne(long id);

    User getUserByUsername(String email);

    void save(User user);

    void update(long id, User updatedUser);

    void delete(long id);
}
