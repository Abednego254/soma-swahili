package com.abednego.somaSwahili.dto;

import com.abednego.somaSwahili.model.other.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private Role role;

    private boolean isVerified;

    private boolean isActive;
}
