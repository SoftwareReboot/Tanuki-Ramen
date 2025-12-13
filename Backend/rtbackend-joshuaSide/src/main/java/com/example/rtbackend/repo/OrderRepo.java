package com.example.rtbackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rtbackend.domain.entities.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByStatus(String status);
    
}
