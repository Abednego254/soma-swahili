package com.abednego.somaSwahili.service;

import com.abednego.somaSwahili.dto.AdminRegistrationDto;
import com.abednego.somaSwahili.model.Admin;
import com.abednego.somaSwahili.dto.AdminUpdateDto;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin save(AdminRegistrationDto dto);
    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
    List<Admin> findAll();
    Admin update(Long id, AdminUpdateDto updatedAdmin);
    void delete(Long id);
}
