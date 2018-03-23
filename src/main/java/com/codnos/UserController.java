package com.codnos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Flux<MongoUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users-without-salaries")
    public Flux<MongoUser> getAllUsersWithoutSalaries() {
        return userRepository.findAllExcludingSalaries();
    }
}
