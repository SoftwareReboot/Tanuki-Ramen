package com.example.rtbackend.services.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.example.rtbackend.domain.entities.Role;
import com.example.rtbackend.domain.entities.User;
import com.example.rtbackend.repo.RoleRepo;
import com.example.rtbackend.repo.UserRepo;
import com.example.rtbackend.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
    }

    @Override
    public User createUser(User user) {
        // Validate required fields
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        
        // Check if username already exists
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + user.getUsername());
        }
        
        // If roleName is provided in the transient field, find and set the role
        if (user.getRoleName() != null && !user.getRoleName().isEmpty()) {
            Role role = roleRepo.findByRoleName(user.getRoleName())
                    .orElseThrow(() -> new NoSuchElementException("Role not found: " + user.getRoleName()));
            user.setRole(role);
        }
        
        // Validate that role is set
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
        
        // Save user
        return userRepo.save(user);
    }

    @Override 
    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        
        // Update username if provided and different
        if (updatedUser.getUsername() != null && !updatedUser.getUsername().trim().isEmpty()) {
            // Check if new username is already taken by another user
            if (!user.getUsername().equals(updatedUser.getUsername()) && 
                userRepo.findByUsername(updatedUser.getUsername()).isPresent()) {
                throw new IllegalArgumentException("Username already exists: " + updatedUser.getUsername());
            }
            user.setUsername(updatedUser.getUsername());
        }
        
        // Update name if provided
        if (updatedUser.getName() != null && !updatedUser.getName().trim().isEmpty()) {
            user.setName(updatedUser.getName());
        }
        
        // If roleName is provided, update the role
        if (updatedUser.getRoleName() != null && !updatedUser.getRoleName().isEmpty()) {
            Role role = roleRepo.findByRoleName(updatedUser.getRoleName())
                    .orElseThrow(() -> new NoSuchElementException("Role not found: " + updatedUser.getRoleName()));
            user.setRole(role);
        } else if (updatedUser.getRole() != null) {
            user.setRole(updatedUser.getRole());
        }
        
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        // Check if user exists before deleting
        if (!userRepo.existsById(id)) {
            throw new NoSuchElementException("User not found with id: " + id);
        }
        userRepo.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + username));
    }
}