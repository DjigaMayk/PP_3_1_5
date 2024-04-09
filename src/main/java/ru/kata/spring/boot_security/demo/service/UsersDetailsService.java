package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.security.UsersDetails;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final UserServiceImpl personServiceImpl;

    @Autowired
    public UsersDetailsService(UserServiceImpl personServiceImpl) {
        this.personServiceImpl = personServiceImpl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> person = personServiceImpl.getPersonByName(username);
        if (person.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new UsersDetails(person.get());
    }
}
