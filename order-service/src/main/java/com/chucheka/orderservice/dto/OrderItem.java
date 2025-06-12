package com.chucheka.orderservice.dto;

import lombok.Data;

@Data
public class OrderItem {
    private String itemCode;
    private Integer quantity;
}
