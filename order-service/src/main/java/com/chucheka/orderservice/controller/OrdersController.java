package com.chucheka.orderservice.controller;

import com.chucheka.orderservice.dto.CustomerOrder;
import com.chucheka.orderservice.dto.GenericResponse;
import com.chucheka.orderservice.entities.Order;
import com.chucheka.orderservice.services.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderService orderService;

    @GetMapping("")
    public String getOrder() {
        return "Order service";
    }

    @PostMapping("")
    public GenericResponse<Order> createOrder(@RequestBody @Valid CustomerOrder customerOrder) {

        return orderService.createOrder(customerOrder);

    }

    @DeleteMapping("/{orderCode}")
    public void deleteOrder(@PathVariable("orderCode") String orderCode) {
        orderService.deleteOrder(orderCode);

    }
}
