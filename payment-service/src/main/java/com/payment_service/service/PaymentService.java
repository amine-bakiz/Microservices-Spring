package com.payment_service.service;

import com.payment_service.dto.PaymentRequest;
import com.payment_service.feignclients.OrderClient;
import com.payment_service.model.OrderStatus;
import com.payment_service.model.Payment;
import com.payment_service.model.PaymentStatus;
import com.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;

    public Payment processPayment(PaymentRequest request) {
        // Validate payment details
        validatePayment(request);

        // Mock payment gateway integration
        boolean paymentSuccess = mockPaymentGateway(request);

        Payment payment = Payment.builder()
                .orderId(request.orderId())
                .amount(request.amount())
                .paymentMethod(request.paymentMethod())
                .status(paymentSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                .paymentDate(LocalDateTime.now())
                .transactionId(UUID.randomUUID().toString())
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        orderClient.updateOrderStatus(
                request.orderId(),
                paymentSuccess ? OrderStatus.CONFIRMED : OrderStatus.PAYMENT_FAILED
        );

        return savedPayment;
    }

    private void validatePayment(PaymentRequest request) {
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid payment amount");
        }
    }

    private boolean mockPaymentGateway(PaymentRequest request) {
        // Simulate 80% success rate
        return Math.random() < 0.8;
    }
    public List<Payment> getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }


}
