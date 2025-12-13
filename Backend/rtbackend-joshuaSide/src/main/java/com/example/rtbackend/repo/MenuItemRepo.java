package com.example.rtbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rtbackend.domain.entities.MenuItem;

public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {
    
}
