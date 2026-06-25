package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.wallet.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {
    List<WalletTransaction> findByWalletId(Long walletId);
    List<WalletTransaction> findByWalletStudentId(Long studentId);
    Optional<WalletTransaction> findByReferenceId(String referenceId);
}
