package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.model.Tutor;
import com.abednego.somaSwahili.dto.TutorRegistrationDto;
import com.abednego.somaSwahili.dto.TutorUpdateDto;

import java.util.List;
import java.util.Optional;

public interface EmployerService {
    Tutor save(TutorRegistrationDto dto);
    Optional<Tutor> findById(Long id);
    Optional<Tutor> findByEmail(String email);
    List<Tutor> findAll();
    Tutor update(Long id, TutorUpdateDto updatedEmployer);
    void delete(Long id);
}
