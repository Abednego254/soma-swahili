package com.abednego.somaSwahili.service.impl;

import com.abednego.somaSwahili.dto.TutorResponseDTO;
import com.abednego.somaSwahili.dto.TutorRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.Tutor;
import com.abednego.somaSwahili.model.Role;
import com.abednego.somaSwahili.repository.EmployerRepository;
import com.abednego.somaSwahili.service.EmployerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;
    private final PasswordEncoder passwordEncoder; // inject encoder

    @Override
    public Tutor save(TutorRequestDTO dto) {
        log.info("Saving new employer: {}", dto.getEmail());

        Tutor tutor = new Tutor();
        tutor.setFirstName(dto.getFirstName());
        tutor.setLastName(dto.getLastName());
        tutor.setEmail(dto.getEmail());
        tutor.setPassword(passwordEncoder.encode(dto.getPassword())); // 🔐 hash
        tutor.setPhone(dto.getPhone());
        tutor.setCompanyName(dto.getCompanyName());
        tutor.setCompanyWebsite(dto.getCompanyWebsite());
        tutor.setContactPerson(dto.getContactPerson());
        tutor.setIndustry(dto.getIndustry());
        tutor.setLocation(dto.getLocation());
        tutor.setRole(Role.EMPLOYER); // 👔 set role

        return employerRepository.save(tutor);
    }

    @Override
    public Optional<Tutor> findById(Long id) {
        log.info("Fetching employer by ID: {}", id);
        return employerRepository.findById(id);
    }

    @Override
    public Optional<Tutor> findByEmail(String email) {
        log.info("Fetching employer by email: {}", email);
        return employerRepository.findByEmail(email);
    }

    @Override
    public List<Tutor> findAll() {
        log.info("Fetching all employers");
        return employerRepository.findAll();
    }

    @Override
    public Tutor update(Long id, TutorResponseDTO updatedEmployer) {
        log.info("Updating employer with ID: {}", id);
        Tutor existingTutor = employerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employer not found with ID: " + id));

        if(updatedEmployer.getFirstName() != null)
            existingTutor.setFirstName(updatedEmployer.getFirstName());

        if(updatedEmployer.getLastName() != null)
            existingTutor.setLastName(updatedEmployer.getLastName());

        if(existingTutor.getPhone() != null)
            existingTutor.setPhone(updatedEmployer.getPhone());

        if(existingTutor.getCompanyName() != null)
            existingTutor.setCompanyName(updatedEmployer.getCompanyName());

        if(existingTutor.getEmail() != null)
            existingTutor.setEmail(updatedEmployer.getEmail());

        if(existingTutor.getContactPerson() != null)
            existingTutor.setContactPerson(updatedEmployer.getContactPerson());

        if(existingTutor.getLocation() != null)
            existingTutor.setLocation(updatedEmployer.getLocation());

        return employerRepository.save(existingTutor);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting employer with ID: {}", id);
        if (!employerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employer not found with ID: " + id);
        }
        employerRepository.deleteById(id);
    }
}
