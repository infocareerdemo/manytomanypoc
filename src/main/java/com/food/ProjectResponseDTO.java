package com.food;

import java.util.Set;

import lombok.Data;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String projectName;
    private String technologyUsed;
    private Set<EmployeeResponseDTO> employees; // Use EmployeeResponseDTO type

    @Data
    public static class EmployeeResponseDTO {
        private Long id;
        private String name;
        private String email;
        private String technicalSkill;
    }
}


