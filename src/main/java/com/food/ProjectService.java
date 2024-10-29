package com.food;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {

	
	@Autowired
	ProjectRepository projectRepository;

	public List<Project> getAllEmployees() {
		return projectRepository.findAll();
	}
	
	

    @Autowired
    private EmployeeRepository employeeRepository;
	
	
	
	@Transactional
    public Project addProjectWithEmployees(ProjectDTO projectDTO) {
        // Create a new Project
        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        project.setTechnologyUsed(projectDTO.getTechnologyUsed());

        // Fetch the list of employees based on employeeIds from DTO
        Set<Employee> employees = new HashSet<>();
        for (Long employeeId : projectDTO.getEmployeeIds()) {
            employeeRepository.findById(employeeId).ifPresent(employees::add);
        }

        // Set the employees to the project
        project.setEmployees(employees);

        // Associate each employee with the project (the bidirectional relationship)
        for (Employee employee : employees) {
            employee.getProjects().add(project);
        }

        // Save the project (this will also save the relationship in the join table)
        return projectRepository.save(project);
    }
	
	
	 public ProjectResponseDTO convertToDTO(Project project) {
	        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
	        projectResponseDTO.setId(project.getId());
	        projectResponseDTO.setProjectName(project.getProjectName());
	        projectResponseDTO.setTechnologyUsed(project.getTechnologyUsed());

	        // Convert employees to EmployeeResponseDTO
	        Set<ProjectResponseDTO.EmployeeResponseDTO> employeeResponseDTOs = new HashSet<>();
	        
	        for (Employee emp : project.getEmployees()) {
	            ProjectResponseDTO.EmployeeResponseDTO empDTO = new ProjectResponseDTO.EmployeeResponseDTO();
	            empDTO.setId(emp.getId());
	            empDTO.setName(emp.getName());
	            empDTO.setEmail(emp.getEmail());
	            empDTO.setTechnicalSkill(emp.getTechnicalSkill());
	            employeeResponseDTOs.add(empDTO);
	        }
	        
	        projectResponseDTO.setEmployees(employeeResponseDTOs);
	        return projectResponseDTO;
	    }

	
}
