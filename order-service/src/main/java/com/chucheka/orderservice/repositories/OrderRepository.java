package com.chucheka.orderservice.repositories;

import com.chucheka.orderservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> {
    Order findByOrderCode(String orderCode);
}
