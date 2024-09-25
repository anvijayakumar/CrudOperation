package com.example.crud.operation.service;

import com.example.crud.operation.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    ResponseEntity<Object> getUserById(Long id);

    ResponseEntity<Object> updateUser(Long id, User userDetails);

    ResponseEntity<String> deleteUser(Long id);
}
