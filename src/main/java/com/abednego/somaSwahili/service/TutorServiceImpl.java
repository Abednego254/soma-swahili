package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.tutor.TutorResponseDTO;
import com.abednego.somaSwahili.dto.tutor.TutorRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.tutor.Tutor;
import com.abednego.somaSwahili.model.tutor.TutorStatus;
import com.abednego.somaSwahili.model.tutor.TutorWallet;
import com.abednego.somaSwahili.model.tutor.TutorDocument;
import com.abednego.somaSwahili.model.other.Role;
import com.abednego.somaSwahili.repository.TutorRepository;
import com.abednego.somaSwahili.repository.TutorDocumentRepository;
import com.abednego.somaSwahili.service.TutorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;
    private final TutorDocumentRepository tutorDocumentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Tutor save(TutorRequestDTO dto) {
        log.info("Saving new tutor: {}", dto.getEmail());

        Tutor tutor = new Tutor();
        tutor.setFirstName(dto.getFirstName());
        tutor.setLastName(dto.getLastName());
        tutor.setEmail(dto.getEmail());
        tutor.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐 hash
        tutor.setPhone(dto.getPhone());
        tutor.setHighestQualification(dto.getHighestQualification());
        tutor.setTeachingExperience(dto.getTeachingExperience());
        tutor.setBio(dto.getBio());
        tutor.setVideoUrl(dto.getVideoUrl());
        tutor.setRole(Role.TUTOR); // 🎓 set role to TUTOR
        tutor.setStatus(TutorStatus.PENDING); // Default to pending approval

        // Associate uploaded documents if IDs are provided
        if (dto.getDocumentIds() != null && !dto.getDocumentIds().isEmpty()) {
            List<TutorDocument> documents = tutorDocumentRepository.findAllById(dto.getDocumentIds());
            documents.forEach(doc -> doc.setTutor(tutor));
            tutor.setDocuments(documents);
        }

        // Initialize Tutor Wallet
        TutorWallet wallet = TutorWallet.builder()
                .tutor(tutor)
                .balance(BigDecimal.ZERO)
                .build();
        tutor.setWallet(wallet);

        return tutorRepository.save(tutor);
    }

    @Override
    public Optional<Tutor> findById(Long id) {
        log.info("Fetching tutor by ID: {}", id);
        return tutorRepository.findById(id);
    }

    @Override
    public Optional<Tutor> findByEmail(String email) {
        log.info("Fetching tutor by email: {}", email);
        return tutorRepository.findByEmail(email);
    }

    @Override
    public List<Tutor> findAll() {
        log.info("Fetching all tutors");
        return tutorRepository.findAll();
    }

    @Override
    @Transactional
    public Tutor update(Long id, TutorResponseDTO updatedTutor) {
        log.info("Updating tutor with ID: {}", id);
        Tutor existingTutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with ID: " + id));

        if (updatedTutor.getFirstName() != null)
            existingTutor.setFirstName(updatedTutor.getFirstName());

        if (updatedTutor.getLastName() != null)
            existingTutor.setLastName(updatedTutor.getLastName());

        if (updatedTutor.getPhone() != null)
            existingTutor.setPhone(updatedTutor.getPhone());

        if (updatedTutor.getHighestQualification() != null)
            existingTutor.setHighestQualification(updatedTutor.getHighestQualification());

        if (updatedTutor.getTeachingExperience() != null)
            existingTutor.setTeachingExperience(updatedTutor.getTeachingExperience());

        if (updatedTutor.getBio() != null)
            existingTutor.setBio(updatedTutor.getBio());

        if (updatedTutor.getVideoUrl() != null)
            existingTutor.setVideoUrl(updatedTutor.getVideoUrl());

        if (updatedTutor.getEmail() != null)
            existingTutor.setEmail(updatedTutor.getEmail());

        return tutorRepository.save(existingTutor);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        log.info("Deleting tutor with ID: {}", id);
        if (!tutorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tutor not found with ID: " + id);
        }
        tutorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Tutor updateTutorStatus(Long id, TutorStatus status) {
        log.info("Updating status of tutor with ID {} to {}", id, status);
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor not found with ID: " + id));
        tutor.setStatus(status);
        return tutorRepository.save(tutor);
    }

    @Override
    public List<Tutor> findTutorsByStatus(TutorStatus status) {
        log.info("Fetching tutors with status: {}", status);
        return tutorRepository.findByStatus(status);
    }
}
