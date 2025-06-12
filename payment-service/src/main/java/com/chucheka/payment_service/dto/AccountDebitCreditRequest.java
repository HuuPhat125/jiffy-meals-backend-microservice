package com.chucheka.payment_service.dto;

import com.chucheka.payment_service.enums.PaymentType;
import lombok.Builder;

import java.math.BigDecimal;
@Builder

public record AccountDebitCreditRequest(
        String currencyCode,
        String reference,
        BigDecimal amount,
        String toAccountNumber,
        String fromAccountNumber,
        PaymentType paymentType,

        String narration

) {
}