package com.chucheka.payment_service.repositories;

import com.chucheka.payment_service.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByEmail(String email);

    Wallet findByWalletId(String walletId);
}
