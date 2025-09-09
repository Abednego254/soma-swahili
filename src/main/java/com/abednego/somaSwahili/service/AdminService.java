package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.admin.AdminRequestDTO;
import com.abednego.somaSwahili.model.admin.Admin;
import com.abednego.somaSwahili.dto.admin.AdminResponseDTO;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin save(AdminRequestDTO dto);
    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
    List<Admin> findAll();
    Admin update(Long id, AdminResponseDTO updatedAdmin);
    void delete(Long id);
}
