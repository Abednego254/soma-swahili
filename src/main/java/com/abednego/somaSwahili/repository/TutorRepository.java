package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.tutor.Tutor;
import com.abednego.somaSwahili.model.tutor.TutorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByEmail(String email);
    List<Tutor> findByStatus(TutorStatus status);
}
