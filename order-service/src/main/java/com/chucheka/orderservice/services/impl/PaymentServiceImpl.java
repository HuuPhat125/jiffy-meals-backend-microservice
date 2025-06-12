package com.chucheka.orderservice.services.impl;

import com.chucheka.orderservice.dto.CreditDebitWalletDto;
import com.chucheka.orderservice.dto.GenericResponse;
import com.chucheka.orderservice.exceptions.ServerErrorException;
import com.chucheka.orderservice.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final WebClient webClient;

    @Override
    public ResponseEntity<GenericResponse> makePayment(CreditDebitWalletDto creditDebitWalletDto) {

        return webClient.post()
                .uri(CREDIT_DEBIT_WALLET)
                .body(Mono.just(creditDebitWalletDto), CreditDebitWalletDto.class)
                .retrieve()
                .toEntity(GenericResponse.class)
                .onErrorMap(WebClientRequestException.class, ex -> {
                    ex.printStackTrace();
                    throw new ServerErrorException("issuer inoperative");
                })
                .block();

    }
}
