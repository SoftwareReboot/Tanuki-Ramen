package com.example.rtbackend.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.rtbackend.domain.entities.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{
    List<OrderItem> findByOrder_OrderId(Long orderId);
}
