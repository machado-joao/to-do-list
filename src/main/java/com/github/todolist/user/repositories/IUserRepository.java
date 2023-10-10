package com.github.todolist.user.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.todolist.user.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, UUID> {

    User findByUsername(String username);
}
