package com.github.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class Controller {

    @GetMapping("/getMessage")
    public String getMessage() {
        return "It works!";
    }
}
