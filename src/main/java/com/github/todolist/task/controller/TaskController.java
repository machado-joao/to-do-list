package com.github.todolist.task.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.todolist.task.models.Task;
import com.github.todolist.task.repositories.ITaskRepository;
import com.github.todolist.utils.Utils;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Task newTask, HttpServletRequest request) {

        UUID userId = (UUID) request.getAttribute("userId");
        newTask.setUserId(userId);

        List<String> errors = validateModel(newTask);

        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.toString());
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.taskRepository.save(newTask));
    }

    private List<String> validateModel(Task newTask) {

        List<String> errors = new ArrayList<>();

        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.isAfter(newTask.getStartAt())) {
            errors.add("The start date must be greater than the current date.");
        }

        if (currentDate.isAfter(newTask.getEndAt())) {
            errors.add("The end date must be greater than the current date.");
        }

        if (newTask.getStartAt().isAfter(newTask.getEndAt())) {
            errors.add("The start date must be before the end date.");
        }

        return errors;
    }

    @GetMapping("/getAll")
    public List<Task> getAll(HttpServletRequest request) {

        UUID userId = (UUID) request.getAttribute("userId");

        return this.taskRepository.findByUserId(userId);
    }

    @PutMapping("/update/{taskId}")
    public Task update(@PathVariable UUID taskId, @RequestBody Task task, HttpServletRequest request) {

        var existingTask = this.taskRepository.findById(taskId).orElse(null);
        Utils.copyNonNullProperties(task, existingTask);

        return task;
    }
}
