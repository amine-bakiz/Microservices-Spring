package com.user_service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserAppController {
    private final UserAppRepository repository;

    public UserAppController(UserAppRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<UserApp> getAllUsers() {
        return repository.findAll();
    }

    @PostMapping
    public UserApp createUser(@RequestBody UserApp user) {
        return repository.save(user);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserApp> getUser(@PathVariable Long userId) {
        return repository.findById(userId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
