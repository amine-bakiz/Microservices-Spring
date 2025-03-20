package com.order_service.repository;

import com.order_service.model.Order;
import com.order_service.model.OrederStatus;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Long> {
    @Modifying
    @Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
    void updateStatus(@Param("orderId") Long orderId, @Param("status") OrederStatus status);
}
