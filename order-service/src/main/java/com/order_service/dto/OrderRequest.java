package com.order_service.dto;

import java.util.List;

public record OrderRequest(Long userId,
                           List<OrderItemRequest> items) {
}
