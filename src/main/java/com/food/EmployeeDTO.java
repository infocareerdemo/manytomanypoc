package com.food;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeDTO {

	
    private String name;
    private String email;
    private String technicalSkill;
    private List<Long> projectIds;
}
