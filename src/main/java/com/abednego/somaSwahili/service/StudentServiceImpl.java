package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.student.StudentResponseDTO;
import com.abednego.somaSwahili.dto.student.StudentRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.other.Role;
import com.abednego.somaSwahili.model.other.ProficiencyLevel;
import com.abednego.somaSwahili.model.student.Student;
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
    private final com.abednego.somaSwahili.repository.StudentWalletRepository studentWalletRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Student save(StudentRequestDTO dto) {
        log.info("Saving new student: {}", dto.getEmail());

        Student student = new Student();
        student.setFirstName(dto.getFirstName());
        student.setLastName(dto.getLastName());
        student.setEmail(dto.getEmail());
        student.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐 hash
        student.setPhone(dto.getPhone());
        
        if (dto.getProficiencyLevel() != null) {
            student.setProficiencyLevel(ProficiencyLevel.valueOf(dto.getProficiencyLevel().toUpperCase()));
        }
        student.setLearningGoals(dto.getLearningGoals());
        student.setPreferredLearningStyle(dto.getPreferredLearningStyle());
        student.setAvailability(dto.getAvailability());
        student.setRole(Role.STUDENT); // 🎓 assign role

        // Automatically initialize student wallet with 0.00 balance
        com.abednego.somaSwahili.model.student.StudentWallet wallet = com.abednego.somaSwahili.model.student.StudentWallet.builder()
                .student(student)
                .balance(java.math.BigDecimal.ZERO)
                .build();
        student.setWallet(wallet);

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
    public Student update(Long id, StudentResponseDTO updatedStudent) {
        log.info("Updating student with ID: {}", id);
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));

        if (updatedStudent.getFirstName() != null)
            existingStudent.setFirstName(updatedStudent.getFirstName());

        if (updatedStudent.getLastName() != null)
            existingStudent.setLastName(updatedStudent.getLastName());

        if (updatedStudent.getPhone() != null)
            existingStudent.setPhone(updatedStudent.getPhone());

        if (updatedStudent.getProficiencyLevel() != null) {
            existingStudent.setProficiencyLevel(ProficiencyLevel.valueOf(updatedStudent.getProficiencyLevel().toUpperCase()));
        }

        if (updatedStudent.getLearningGoals() != null) {
            existingStudent.setLearningGoals(updatedStudent.getLearningGoals());
        }

        if (updatedStudent.getPreferredLearningStyle() != null) {
            existingStudent.setPreferredLearningStyle(updatedStudent.getPreferredLearningStyle());
        }

        if (updatedStudent.getAvailability() != null) {
            existingStudent.setAvailability(updatedStudent.getAvailability());
        }

        if (updatedStudent.getEmail() != null)
            existingStudent.setEmail(updatedStudent.getEmail());

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
