package com.food;

import java.util.List;

import lombok.Data;


@Data
public class ProjectDTO {

	private String projectName;
    private String technologyUsed;
    private List<Long> employeeIds; 
}
