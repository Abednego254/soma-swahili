package com.abednego.somaSwahili.dto.admin;

import lombok.Data;
import java.util.List;

@Data
public class AdminResponseDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String adminCode;
    private String department;
    private String adminLevel;       // AdminLevel name
    private Long managerId;          // Optional: manager's ID
    private String managerName;      // Optional: manager's full name
    private List<SubordinateDTO> subordinates; // Optional: list of subordinates
    private String notes;            // Optional internal notes
    private String createdAt;
    private String updatedAt;

    @Data
    public static class SubordinateDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String adminLevel;
    }
}
