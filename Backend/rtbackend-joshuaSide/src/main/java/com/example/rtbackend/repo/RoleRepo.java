package com.example.rtbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rtbackend.domain.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleName(String roleName);
    
}
