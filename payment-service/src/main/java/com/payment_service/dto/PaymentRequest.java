package com.payment_service.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public record PaymentRequest(@NotNull Long orderId,
                             @NotNull  BigDecimal amount,
                              String paymentMethod,
                              String cardNumber,
                              String cvv,
                              String expiryDate) {
}
