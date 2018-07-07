package com.codnos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/users")
    public Flux<MongoUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/api/users-without-salaries")
    public Flux<MongoUser> getAllUsersWithoutSalaries() {
        return userRepository.findAllExcludingSalaries();
    }

    @GetMapping("/api/user/{userId}")
    public Mono<MongoUser> getUser(@PathVariable String userId) {
        return userRepository.findById(userId);
    }
}
