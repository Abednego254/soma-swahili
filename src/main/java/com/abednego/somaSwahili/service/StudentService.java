package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.model.student.Student;
import com.abednego.somaSwahili.dto.student.StudentRequestDTO;
import com.abednego.somaSwahili.dto.student.StudentResponseDTO;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student save(StudentRequestDTO dto);
    Optional<Student> findById(Long id);
    Optional<Student> findByEmail(String email);
    List<Student> findAll();
    Student update(Long id, StudentResponseDTO updatedStudent);
    void delete(Long id);
}
