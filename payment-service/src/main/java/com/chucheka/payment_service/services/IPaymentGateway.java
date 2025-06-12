package com.chucheka.payment_service.services;

import com.chucheka.payment_service.dto.AccountDebitCreditRequest;
import com.chucheka.payment_service.dto.AccountDebitCreditResponse;
import org.springframework.http.ResponseEntity;

public interface IPaymentGateway {

   ResponseEntity<AccountDebitCreditResponse> makePayment(AccountDebitCreditRequest request);

}
