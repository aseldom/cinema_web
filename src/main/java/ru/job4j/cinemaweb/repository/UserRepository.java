package ru.job4j.cinemaweb.repository;

import ru.job4j.cinemaweb.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> save(User user);

    Optional<User> findByEmailAndPassword(String email, String password);

}