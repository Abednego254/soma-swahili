package com.abednego.somaSwahili.controller;

import com.abednego.somaSwahili.dto.TutorResponseDTO;
import com.abednego.somaSwahili.dto.TutorRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.Tutor;
import com.abednego.somaSwahili.service.EmployerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/employers")
@RequiredArgsConstructor
public class EmployerController {

    private final EmployerService employerService;

    @PostMapping
    public ResponseEntity<Tutor> createEmployer(@Valid @RequestBody TutorRequestDTO employerDto) {
        log.info("Creating employer: {}", employerDto.getEmail());
        Tutor createdTutor = employerService.save(employerDto);
        return ResponseEntity.ok(createdTutor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> getEmployerById(@PathVariable Long id) {
        log.info("Fetching employer by ID: {}", id);
        return employerService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found with ID: " + id));
    }

    @GetMapping("/email")
    public ResponseEntity<Tutor> getEmployerByEmail(@RequestParam("value") String email) {
        log.info("Fetching employer by email: {}", email);
        return employerService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found with email: " + email));
    }

    @GetMapping
    public ResponseEntity<List<Tutor>> getAllEmployers() {
        log.info("Fetching all employers");
        return ResponseEntity.ok(employerService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutor> updateEmployer(@PathVariable Long id, @Valid @RequestBody TutorResponseDTO updatedEmployer) {
        log.info("Updating employer with ID: {}", id);
        Tutor existingTutor = employerService.update(id, updatedEmployer);
        return ResponseEntity.ok(existingTutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployer(@PathVariable Long id) {
        log.info("Deleting employer with ID: {}", id);
        employerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
