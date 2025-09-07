package com.abednego.somaSwahili.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin extends User {

    @NotBlank(message = "Admin code is required")
    @Size(min = 4, message = "Admin code must be at least 4 characters")
    @Column(name = "admin_code", unique = true, nullable = false)
    private String adminCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_level", nullable = false)
    private AdminLevel adminLevel;

    // 🔹 Hierarchy: Manager of this admin (null for Super Admin)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private Admin manager;

    // 🔹 Subordinates reporting to this admin
    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Admin> subordinates;

    // 🔹 Optional internal notes
    @Column(length = 500)
    private String notes;
}
