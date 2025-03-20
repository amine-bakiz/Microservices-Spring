package com.payment_service.feignclients;

import com.payment_service.model.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {
    @PutMapping("/api/orders/{orderId}/status")
    void updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status
    );
}
