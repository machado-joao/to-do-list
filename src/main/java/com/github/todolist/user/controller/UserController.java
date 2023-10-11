package com.github.todolist.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.todolist.user.models.User;
import com.github.todolist.user.repositories.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody User newUser) {

        var user = this.userRepository.findByUsername(newUser.getUsername());

        if (user != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already taken.");
        }

        String hash = BCrypt.withDefaults().hashToString(12, newUser.getPassword().toCharArray());
        newUser.setPassword(hash);

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepository.save(newUser));
    }
}
