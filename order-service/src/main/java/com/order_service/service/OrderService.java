package com.order_service.service;

import com.order_service.dto.OrderRequest;
import com.order_service.dto.ProductResponse;
import com.order_service.feignclients.ProductClient;
import com.order_service.feignclients.UserClient;
import com.order_service.model.Order;
import com.order_service.model.OrderEvent;
import com.order_service.model.OrderItem;
import com.order_service.model.OrederStatus;
import com.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@RequiredArgsConstructor
public class OrderService {
    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;

    public Order createOrder(OrderRequest request) {
        // Validate user
        userClient.getUser(request.userId());

        // Check product availability and prices
        List<OrderItem> orderItems = request.items().stream()
                .map(item -> {
                    ProductResponse product = productClient.getProduct(item.productId());
                    if(product.stock() < item.quantity()) {
                        throw new RuntimeException("Insufficient stock for product: " + product.id());
                    }
                    return new OrderItem(
                            item.productId(),
                            item.quantity(),
                            product.price()
                    );
                }).toList();

        // Create order
        Order order = Order.builder()
                .userId(request.userId())
                .orderDate(LocalDateTime.now())
                .status(OrederStatus.PENDING)
                .items(orderItems)
                .build();

        // Update product stock
        orderItems.forEach(item ->
                productClient.updateStock(item.getProductId(), item.getQuantity())
        );


        return order;

    }
    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Order updateOrderStatus(Long orderId, OrederStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        return orderRepository.save(order);
    }

}
