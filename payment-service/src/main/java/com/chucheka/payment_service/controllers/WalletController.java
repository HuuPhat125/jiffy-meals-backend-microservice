package com.chucheka.payment_service.controllers;

import com.chucheka.payment_service.dto.CreateWalletDto;
import com.chucheka.payment_service.dto.CreditDebitWalletDto;
import com.chucheka.payment_service.dto.GenericResponse;
import com.chucheka.payment_service.services.WalletService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    @PostMapping("")
    public GenericResponse createWallet(@RequestBody @Valid CreateWalletDto createWalletDto) {
        return walletService.createWallet(createWalletDto);
    }

    @PostMapping("/fund-wallet")
    public GenericResponse fundWallet(@RequestBody @Valid CreditDebitWalletDto creditDebitWalletDto){
        return walletService.fundWallet(creditDebitWalletDto);
    }

    @PostMapping("/credit-wallet")
    public GenericResponse creditWallet(@RequestBody @Valid CreditDebitWalletDto creditDebitWalletDto){
        return walletService.creditWallet(creditDebitWalletDto);
    }
}
