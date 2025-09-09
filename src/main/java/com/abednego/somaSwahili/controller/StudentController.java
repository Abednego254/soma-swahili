package com.abednego.somaSwahili.controller;

import com.abednego.somaSwahili.dto.student.StudentResponseDTO;
import com.abednego.somaSwahili.dto.student.StudentRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.student.Student;
import com.abednego.somaSwahili.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentRequestDTO studentDto) {
        log.info("Creating student: {}", studentDto.getEmail());
        Student createdStudent = studentService.save(studentDto);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        log.info("Fetching student by ID: {}", id);
        return studentService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    @GetMapping("/email")
    public ResponseEntity<Student> getStudentByEmail(@RequestParam("value") String email) {
        log.info("Fetching student by email: {}", email);
        return studentService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("Fetching all students");
        return ResponseEntity.ok(studentService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentResponseDTO updatedStudent) {
        log.info("Updating student with ID: {}", id);
        Student existingStudent = studentService.update(id, updatedStudent);
        return ResponseEntity.ok(existingStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        log.info("Deleting student with ID: {}", id);
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
