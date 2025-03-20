package com.order_service.dto;

public record UserResponse(Long id,
                           String username,
                           String email) {
}
