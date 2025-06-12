package com.chucheka.payment_service.services;

import com.chucheka.payment_service.dto.AccountDebitCreditRequest;
import com.chucheka.payment_service.dto.AccountDebitCreditResponse;
import com.chucheka.payment_service.exceptions.ServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.server.WebServerException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentGateway implements IPaymentGateway {


    private final WebClient webClient;

    @Override
    public ResponseEntity<AccountDebitCreditResponse> makePayment(AccountDebitCreditRequest request) {

        return webClient.post()
                .uri("/banka/transfer")
                .body(Mono.just(request), AccountDebitCreditRequest.class)
                .retrieve()
                .toEntity(AccountDebitCreditResponse.class)
                .onErrorMap(WebClientRequestException.class, ex -> {
                    throw new ServerErrorException("issuer inoperative");
                })
                .block();

    }
}
