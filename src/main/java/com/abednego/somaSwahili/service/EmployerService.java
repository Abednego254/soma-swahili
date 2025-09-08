package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.model.Tutor;
import com.abednego.somaSwahili.dto.TutorRequestDTO;
import com.abednego.somaSwahili.dto.TutorResponseDTO;

import java.util.List;
import java.util.Optional;

public interface EmployerService {
    Tutor save(TutorRequestDTO dto);
    Optional<Tutor> findById(Long id);
    Optional<Tutor> findByEmail(String email);
    List<Tutor> findAll();
    Tutor update(Long id, TutorResponseDTO updatedEmployer);
    void delete(Long id);
}
