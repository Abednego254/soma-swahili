package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.tutor.TutorWallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TutorWalletRepository extends JpaRepository<TutorWallet, Long> {
    Optional<TutorWallet> findByTutorId(Long tutorId);
}
