package com.example.rtbackend.services;

import java.util.List;

import com.example.rtbackend.domain.entities.Payment;

public interface PaymentService {
    Payment processPayment(Long orderId, String paymentMethod, Long cashierId);
    Payment getPaymentById(Long paymentId);
    Payment getPaymentByOrderId(Long orderId);
    List<Payment> getAllPayments();
    List<Payment> getPaymentsByStatus(String paymentStatus);
}