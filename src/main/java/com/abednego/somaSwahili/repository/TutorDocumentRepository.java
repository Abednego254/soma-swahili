package com.abednego.somaSwahili.repository;

import com.abednego.somaSwahili.model.tutor.TutorDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TutorDocumentRepository extends JpaRepository<TutorDocument, Long> {
    List<TutorDocument> findByTutorId(Long tutorId);
}
