package com.payment_service.dto;

import com.payment_service.model.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(Long id,
                              Long orderId,
                              BigDecimal amount,
                              String transactionId,
                              PaymentStatus status,
                              LocalDateTime paymentDate) {
}
