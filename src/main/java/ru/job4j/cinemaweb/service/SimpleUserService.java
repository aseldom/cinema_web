package ru.job4j.cinemaweb.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinemaweb.model.User;
import ru.job4j.cinemaweb.repository.UserRepository;

import java.util.Optional;

@Service
public class SimpleUserService implements UserService {

    private final UserRepository userRepository;

    public SimpleUserService(UserRepository sql2ouserRepository) {
        this.userRepository = sql2ouserRepository;
    }

    @Override
    public Optional<User> save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
