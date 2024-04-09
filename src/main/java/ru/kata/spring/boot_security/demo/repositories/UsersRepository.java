package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<User, Integer> {

    Optional<User> findByFirstName(String name);

    @Modifying
    @Query("UPDATE User person SET person.firstName = :#{#person.firstName}, person.lastName = :#{#person.lastName}, person.age = :#{#person.age} WHERE person.id = :id")
    void updateUserById(@Param("id") int id, @Param("person") User user);
}




