package com.payment_service.controller;


import com.payment_service.dto.PaymentRequest;
import com.payment_service.model.Payment;
import com.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;


    @PostMapping
    public ResponseEntity<Payment> processPayment(
            @RequestBody PaymentRequest request
    ) {
        return ResponseEntity.ok(paymentService.processPayment(request));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<Payment>> getPaymentByOrderId(
            @PathVariable Long orderId
    ) {
        List<Payment> payments = paymentService.getPaymentByOrderId(orderId);
        return payments.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(payments);
    }
}

