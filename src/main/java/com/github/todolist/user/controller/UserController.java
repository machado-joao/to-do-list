package com.github.todolist.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.todolist.user.models.User;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/create")
    public String create(@RequestBody User user) {
        return "User created.";
    }
}
