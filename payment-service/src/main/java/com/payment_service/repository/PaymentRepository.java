package com.payment_service.repository;

import com.payment_service.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findByOrderId(Long orderId);
}
