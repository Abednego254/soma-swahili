package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.model.tutor.Tutor;
import com.abednego.somaSwahili.dto.tutor.TutorRequestDTO;
import com.abednego.somaSwahili.dto.tutor.TutorResponseDTO;

import java.util.List;
import java.util.Optional;

public interface TutorService {
    Tutor save(TutorRequestDTO dto);
    Optional<Tutor> findById(Long id);
    Optional<Tutor> findByEmail(String email);
    List<Tutor> findAll();
    Tutor update(Long id, TutorResponseDTO updatedTutor);
    void delete(Long id);
}
