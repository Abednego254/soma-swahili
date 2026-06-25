package com.abednego.somaSwahili.controller;

import com.abednego.somaSwahili.dto.tutor.TutorResponseDTO;
import com.abednego.somaSwahili.dto.tutor.TutorRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.tutor.Tutor;
import com.abednego.somaSwahili.model.tutor.TutorStatus;
import com.abednego.somaSwahili.service.TutorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/tutors")
@RequiredArgsConstructor
public class TutorController {

    private final TutorService tutorService;

    @PostMapping
    public ResponseEntity<Tutor> createTutor(@Valid @RequestBody TutorRequestDTO tutorDto) {
        log.info("Creating tutor: {}", tutorDto.getEmail());
        Tutor createdTutor = tutorService.save(tutorDto);
        return ResponseEntity.ok(createdTutor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tutor> getTutorById(@PathVariable Long id) {
        log.info("Fetching tutor by ID: {}", id);
        return tutorService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with ID: " + id));
    }

    @GetMapping("/email")
    public ResponseEntity<Tutor> getTutorByEmail(@RequestParam("value") String email) {
        log.info("Fetching tutor by email: {}", email);
        return tutorService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with email: " + email));
    }

    @GetMapping
    public ResponseEntity<List<Tutor>> getAllTutors() {
        log.info("Fetching all tutors");
        return ResponseEntity.ok(tutorService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tutor> updateTutor(@PathVariable Long id, @Valid @RequestBody TutorResponseDTO updatedTutor) {
        log.info("Updating tutor with ID: {}", id);
        Tutor existingTutor = tutorService.update(id, updatedTutor);
        return ResponseEntity.ok(existingTutor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTutor(@PathVariable Long id) {
        log.info("Deleting tutor with ID: {}", id);
        tutorService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 🔹 Endpoint for Admins to view tutors filtered by status (e.g. PENDING)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Tutor>> getTutorsByStatus(@PathVariable("status") TutorStatus status) {
        log.info("Fetching tutors by status: {}", status);
        return ResponseEntity.ok(tutorService.findTutorsByStatus(status));
    }

    // 🔹 Endpoint for Admins to approve/reject tutors
    @PutMapping("/{id}/status")
    public ResponseEntity<Tutor> updateTutorStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") TutorStatus status) {
        log.info("Request to change tutor {} status to {}", id, status);
        Tutor updatedTutor = tutorService.updateTutorStatus(id, status);
        return ResponseEntity.ok(updatedTutor);
    }
}
