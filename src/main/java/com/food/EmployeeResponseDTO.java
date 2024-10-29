package com.food;

import java.util.Set;


import lombok.Data;

@Data
public class EmployeeResponseDTO {
	 private Long id;
     private String name;
     private String email;
     private String technicalSkill;
	 private Set<ProjectResponseDTO> projects; 

     
     
     @Data
     public static class ProjectResponseDTO {
    	 private Long id;
    	    private String projectName;
    	    private String technologyUsed;
     }
 
}
