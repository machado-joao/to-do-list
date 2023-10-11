package com.github.todolist.task.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.todolist.task.models.Task;

public interface ITaskRepository extends JpaRepository<Task, UUID> {
}
