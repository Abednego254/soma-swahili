package com.abednego.somaSwahili.service.impl;

import com.abednego.somaSwahili.dto.AdminResponseDTO;
import com.abednego.somaSwahili.dto.AdminRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.Admin;
import com.abednego.somaSwahili.model.Role;
import com.abednego.somaSwahili.repository.AdminRepository;
import com.abednego.somaSwahili.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Admin save(AdminRequestDTO dto) {
        log.info("Saving new admin: {}", dto.getEmail());

        Admin admin = new Admin();
        admin.setFirstName(dto.getFirstName());
        admin.setLastName(dto.getLastName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setPhone(dto.getPhone());
        admin.setAdminCode(dto.getAdminCode());
        admin.setDepartment(dto.getDepartment());
        admin.setAdminLevel(dto.getAdminLevel());
        admin.setRole(Role.ADMIN);

        return adminRepository.save(admin);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        log.info("Fetching admin by ID: {}", id);
        return adminRepository.findById(id);
    }

    @Override
    public Optional<Admin> findByEmail(String email) {
        log.info("Fetching admin by email: {}", email);
        return adminRepository.findByEmail(email);
    }

    @Override
    public List<Admin> findAll() {
        log.info("Fetching all admins");
        return adminRepository.findAll();
    }

    @Override
    public Admin update(Long id, AdminResponseDTO updatedAdmin) {
    log.info("Updating admin with ID: {}", id);
    Admin existingAdmin = adminRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + id));

    if (updatedAdmin.getFirstName() != null)
        existingAdmin.setFirstName(updatedAdmin.getFirstName());

    if (updatedAdmin.getLastName() != null)
        existingAdmin.setLastName(updatedAdmin.getLastName());

    if (updatedAdmin.getPhone() != null)
        existingAdmin.setPhone(updatedAdmin.getPhone());

    if (updatedAdmin.getAdminLevel() != null)
        existingAdmin.setAdminLevel(updatedAdmin.getAdminLevel());

    if (updatedAdmin.getDepartment() != null)
        existingAdmin.setDepartment(updatedAdmin.getDepartment());

    if (updatedAdmin.getAdminCode() != null)
        existingAdmin.setAdminCode(updatedAdmin.getAdminCode());

    if (updatedAdmin.getRole() != null)
        existingAdmin.setRole(updatedAdmin.getRole());

    if (updatedAdmin.getEmail() != null)
        existingAdmin.setEmail(updatedAdmin.getEmail());

    if (updatedAdmin.getPassword() != null)
        existingAdmin.setPassword(passwordEncoder.encode(updatedAdmin.getPassword())); // encrypt if applicable

    return adminRepository.save(existingAdmin);
    }

    @Override
    public void delete(Long id) {
        log.info("Deleting admin with ID: {}", id);
        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin not found with ID: " + id);
        }
        adminRepository.deleteById(id);
    }
}
