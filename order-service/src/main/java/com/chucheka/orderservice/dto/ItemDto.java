package com.chucheka.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemDto(
        @NotBlank(message = "name of item is required")
        String name,

        @NotNull(message = "price of item is required")
        Double price,
        @NotBlank(message = "image url  of item is required")
        String imageUrl,
        @NotBlank(message = "restaurant code of item is required")
        String restaurantCode
) {
}