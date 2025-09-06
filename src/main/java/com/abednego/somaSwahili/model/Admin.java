package com.abednego.somaSwahili.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotBlank(message = "Department is required")
    @Size(min = 2, message = "Department name must be at least 2 characters")
    @Column(name = "department", nullable = false)
    private String department;

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_level", nullable = false)
    private AdminLevel adminLevel;
}
