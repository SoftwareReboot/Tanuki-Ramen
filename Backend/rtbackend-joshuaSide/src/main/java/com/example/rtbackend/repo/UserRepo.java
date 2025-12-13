package com.example.rtbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rtbackend.domain.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    Optional<User> findByRoleName(String roleName);
}
