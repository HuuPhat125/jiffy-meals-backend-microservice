package com.chucheka.payment_service.services;

import com.chucheka.payment_service.dto.*;
import com.chucheka.payment_service.entities.Wallet;
import com.chucheka.payment_service.enums.PaymentType;
import com.chucheka.payment_service.repositories.WalletRepository;
import com.chucheka.payment_service.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class WalletService {

    @Value("${jiffy.meals.gl.account.number}")
    private String glAccountNumber;

    private final WalletRepository walletRepository;

    private final PaymentGateway paymentGateway;

    public GenericResponse<?> createWallet(CreateWalletDto createWalletDto) {

        Wallet wallet = new Wallet();
        wallet.setWalletId(AppUtils.generateWalletId());
        wallet.setEmail(createWalletDto.email());
        wallet.setUsername(createWalletDto.username());
        wallet.setBlocked(false);
        wallet.setBalance(BigDecimal.ZERO);

        wallet = walletRepository.save(wallet);

        return new GenericResponse<>(true, "Wallet with wallet id " + wallet.getWalletId() + " created", null);
    }

    public GenericResponse<?> fundWallet(CreditDebitWalletDto creditDebitWalletDto) {

        BigDecimal amount = BigDecimal.valueOf(creditDebitWalletDto.amount());

        Wallet wallet = walletRepository.findByWalletId(creditDebitWalletDto.walletId());

        if (!Objects.nonNull(wallet))
            return new GenericResponse<>(false, "wallet not found", null);

        AccountDebitCreditRequest debitCreditRequest = AccountDebitCreditRequest.builder()

                .amount(amount)
                .currencyCode("NGN")
                .fromAccountNumber(creditDebitWalletDto.walletId())
                .paymentType(PaymentType.CREDIT)
                .reference("JM-" + System.currentTimeMillis())
                .toAccountNumber(glAccountNumber)
                .narration(creditDebitWalletDto.narration())
                .build();

        ResponseEntity<AccountDebitCreditResponse> response = paymentGateway.makePayment(debitCreditRequest);

        if (response==null)
            return new GenericResponse<>(false, "issuer inoperative", null);

        AccountDebitCreditResponse res = response.getBody();
        if (!res.code().equals("00"))
            return new GenericResponse<>(false, "issuer inoperative", null);

        BigDecimal newBalance = wallet.getBalance().add(amount);

        wallet.setBalance(newBalance);

        walletRepository.save(wallet);

        return new GenericResponse<>(true, "Wallet successfully funded with " + amount, null);

    }

    public GenericResponse creditWallet(CreditDebitWalletDto creditDebitWalletDto) {

        BigDecimal amount = BigDecimal.valueOf(creditDebitWalletDto.amount());

        Wallet wallet = walletRepository.findByWalletId(creditDebitWalletDto.walletId());

        if (!Objects.nonNull(wallet))
            return new GenericResponse<>(false, "wallet not found", null);

        AccountDebitCreditRequest debitCreditRequest = AccountDebitCreditRequest.builder()

                .amount(amount)
                .currencyCode("NGN")
                .fromAccountNumber(glAccountNumber)
                .paymentType(PaymentType.DEBIT)
                .reference("JM-" + System.currentTimeMillis())
                .toAccountNumber(creditDebitWalletDto.walletId())
                .narration(creditDebitWalletDto.narration())
                .build();

        ResponseEntity<AccountDebitCreditResponse> response = paymentGateway.makePayment(debitCreditRequest);

        if (response==null)
            return new GenericResponse<>(false, "issuer inoperative", null);

        AccountDebitCreditResponse res = response.getBody();

        if (!res.code().equals("00"))
            return new GenericResponse<>(false, "issuer inoperative", null);

        BigDecimal newBalance = wallet.getBalance().subtract(amount);

        wallet.setBalance(newBalance);

        walletRepository.save(wallet);

        return new GenericResponse<>(true, "Wallet successfully debited with " + amount, null);

    }
}
