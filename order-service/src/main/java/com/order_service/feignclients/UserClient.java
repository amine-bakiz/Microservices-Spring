package com.order_service.feignclients;


import com.order_service.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/api/users/{userId}")
    UserResponse getUser(@PathVariable Long userId);
}
