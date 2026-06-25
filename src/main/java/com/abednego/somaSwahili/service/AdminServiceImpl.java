package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.admin.AdminResponseDTO;
import com.abednego.somaSwahili.dto.admin.AdminRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.admin.Admin;
import com.abednego.somaSwahili.model.admin.AdminLevel;
import com.abednego.somaSwahili.model.other.Role;
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
        if (dto.getAdminLevel() != null) {
            admin.setAdminLevel(AdminLevel.valueOf(dto.getAdminLevel().toUpperCase()));
        }
        if (dto.getManagerId() != null) {
            Admin manager = adminRepository.findById(dto.getManagerId())
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found with ID: " + dto.getManagerId()));
            admin.setManager(manager);
        }
        admin.setNotes(dto.getNotes());
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
            existingAdmin.setAdminLevel(AdminLevel.valueOf(updatedAdmin.getAdminLevel().toUpperCase()));

        if (updatedAdmin.getAdminCode() != null)
            existingAdmin.setAdminCode(updatedAdmin.getAdminCode());

        if (updatedAdmin.getEmail() != null)
            existingAdmin.setEmail(updatedAdmin.getEmail());

        if (updatedAdmin.getNotes() != null)
            existingAdmin.setNotes(updatedAdmin.getNotes());

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
