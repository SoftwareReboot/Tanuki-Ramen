package com.example.rtbackend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.rtbackend.domain.entities.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByPaymentStatus(String paymentStatus);
    Optional<Payment> findByOrder_OrderId(Long orderId);
}