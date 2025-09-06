package com.abednego.somaSwahili.service.impl;

import com.abednego.somaSwahili.dto.StudentUpdateDto;
import com.abednego.somaSwahili.dto.StudentRegistrationDto;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.Role;
import com.abednego.somaSwahili.model.Student;
import com.abednego.somaSwahili.repository.StudentRepository;
import com.abednego.somaSwahili.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Student save(StudentRegistrationDto dto) {
        log.info("Saving new student: {}", dto.getEmail());

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐 hash
        student.setPhone(dto.getPhone());
        student.setInstitution(dto.getInstitution());
        student.setCourse(dto.getCourse());
        student.setYearOfStudy(dto.getYearOfStudy());
        student.setRegistrationNumber(dto.getRegistrationNumber());
        student.setRole(Role.STUDENT); // 🎓 assign role

        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        log.info("Fetching student by ID: {}", id);
        return studentRepository.findById(id);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        log.info("Fetching student by email: {}", email);
        return studentRepository.findByEmail(email);
    }

    @Override
    public List<Student> findAll() {
        log.info("Fetching all students");
        return studentRepository.findAll();
    }

     @Override
    public Student update(Long id, StudentUpdateDto updatedStudent) {
    log.info("Updating student with ID: {}", id);
    Student existingStudent = studentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

    if (updatedStudent.getFirstName() != null)
        existingStudent.setFirstName(updatedStudent.getFirstName());

    if (updatedStudent.getLastName() != null)
        existingStudent.setLastName(updatedStudent.getLastName());

    if (updatedStudent.getPhone() != null)
        existingStudent.setPhone(updatedStudent.getPhone());

    if (updatedStudent.getCourse() != null)
        existingStudent.setCourse(updatedStudent.getCourse());

    if (updatedStudent.getRegistrationNumber() != null)
        existingStudent.setRegistrationNumber(updatedStudent.getRegistrationNumber());

    if (updatedStudent.getInstitution() != null)
        existingStudent.setInstitution(updatedStudent.getInstitution());

    if (updatedStudent.getRole() != null)
        existingStudent.setRole(updatedStudent.getRole());

    if (updatedStudent.getEmail() != null)
        existingStudent.setEmail(updatedStudent.getEmail());

    if (updatedStudent.getPassword() != null)
        existingStudent.setPassword(passwordEncoder.encode(updatedStudent.getPassword())); // encrypt if applicable

    return studentRepository.save(existingStudent);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting student with ID: {}", id);
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
    }
}
