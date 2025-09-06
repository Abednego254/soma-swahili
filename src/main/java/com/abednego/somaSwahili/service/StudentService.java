package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.model.Student;
import com.abednego.somaSwahili.dto.StudentRegistrationDto;
import com.abednego.somaSwahili.dto.StudentUpdateDto;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student save(StudentRegistrationDto dto);
    Optional<Student> findById(Long id);
    Optional<Student> findByEmail(String email);
    List<Student> findAll();
    Student update(Long id, StudentUpdateDto updatedStudent);
    void delete(Long id);
}
