package com.abednego.somaSwahili.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@PrimaryKeyJoinColumn(name = "id") // join inheritance using same User ID
public class Tutor extends User {

    @NotBlank(message = "Company name is required")
    @Column(nullable = false)
    private String companyName;

    @Column
    private String companyWebsite;

    @NotBlank(message = "Contact person name is required")
    @Column(nullable = false)
    private String contactPerson;

    @Column
    private String industry;

    @Column
    private String location;
}
