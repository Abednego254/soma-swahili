package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.tutor.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByEmail(String email);
}
