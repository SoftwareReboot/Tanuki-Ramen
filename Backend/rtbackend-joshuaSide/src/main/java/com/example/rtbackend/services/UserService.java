package com.example.rtbackend.services;

import com.example.rtbackend.domain.entities.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(Long id);

    User createUser(User user);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    User findByUsername(String username);
}