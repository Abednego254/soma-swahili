package com.abednego.somaSwahili.controller;

import com.abednego.somaSwahili.dto.admin.AdminResponseDTO;
import com.abednego.somaSwahili.dto.admin.AdminRequestDTO;
import com.abednego.somaSwahili.exception.ResourceNotFoundException;
import com.abednego.somaSwahili.model.admin.Admin;
import com.abednego.somaSwahili.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<Admin> createAdmin(@Valid @RequestBody AdminRequestDTO adminDto) {
        log.info("Creating admin: {}", adminDto.getEmail());
        Admin createdAdmin = adminService.save(adminDto);
        return ResponseEntity.ok(createdAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        log.info("Fetching admin by ID: {}", id);
        return adminService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with ID: " + id));
    }

    @GetMapping("/email")
    public ResponseEntity<Admin> getAdminByEmail(@RequestParam("value") String email) {
        log.info("Fetching admin by email: {}", email);
        return adminService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + email));
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        log.info("Fetching all admins");
        return ResponseEntity.ok(adminService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable Long id, @Valid @RequestBody AdminResponseDTO updatedAdmin) {
        log.info("Updating admin with ID: {}", id);
        Admin existingAdmin = adminService.update(id, updatedAdmin);
        return ResponseEntity.ok(existingAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        log.info("Deleting admin with ID: {}", id);
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
