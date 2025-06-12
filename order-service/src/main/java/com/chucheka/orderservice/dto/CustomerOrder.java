package com.chucheka.orderservice.dto;

import com.chucheka.orderservice.entities.CustomerDetails;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerOrder(
        @NotNull(message = "customer details is required")
        CustomerDetails customerDetails,

        @NotEmpty(message = "order items required")
        List<OrderItem> orderItems,
        Boolean packaged) {
}
