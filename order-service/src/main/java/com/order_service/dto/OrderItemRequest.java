package com.order_service.dto;

import org.antlr.v4.runtime.misc.NotNull;

public record OrderItemRequest(Long productId,
                               @NotNull Integer quantity
) {
}
