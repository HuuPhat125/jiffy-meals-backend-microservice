package com.chucheka.payment_service.dto;


import jakarta.validation.constraints.NotEmpty;

public record CreateWalletDto(
        @NotEmpty(message = "customer username is required")
        String username,
        @NotEmpty(message = "customer email is required")
        String email) {

}
