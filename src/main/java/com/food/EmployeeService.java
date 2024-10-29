package com.food;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	

    @Autowired
    private ProjectRepository projectRepository;

    public Employee createEmployeeWithProjects(Employee employee, List<Long> projectIds) {
        List<Project> projects = projectRepository.findAllById(projectIds);
        for (Project project : projects) {
            employee.getProjects().add(project);
            project.getEmployees().add(employee);
        }
        return employeeRepository.save(employee);
    }

   /* public Project createProjectWithEmployees(Project project, List<Long> employeeIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        for (Employee employee : employees) {
            project.getEmployees().add(employee);
            employee.getProjects().add(project);
        }
        return projectRepository.save(project);
    }*/

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }


    public Employee getEmployeeById(Long id) {
        return employeeRepository.findByIdWithProjects(id).orElse(null);  // Returns null if not found
    }

    
    public Employee getEmployeeWithProjects(Long employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employee -> {
                    // Make sure the employee has their projects populated
                    employee.getProjects().size(); // this triggers lazy loading of projects
                    return employee;
                })
                .orElse(null); // Return null if not found
    }
    
    public List<ProjectResponseDTO> getProjectsByEmployeeId(Long employeeId) {
        // Get the employee object by its ID
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee == null) {
            return List.of(); // Return an empty list if employee not found
        }

        // Map the employee's associated projects to ProjectResponseDTO
        return employee.getProjects().stream()
                .map(project -> {
                    ProjectResponseDTO projectDTO = new ProjectResponseDTO();
                    projectDTO.setId(project.getId());
                    projectDTO.setProjectName(project.getProjectName());
                    projectDTO.setTechnologyUsed(project.getTechnologyUsed());
                    return projectDTO;
                })
                .collect(Collectors.toList());
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
	 
    
		
	 public EmployeeResponseDTO convertToDTO(Employee employee) {
		 EmployeeResponseDTO employeeResponseDTO = new EmployeeResponseDTO();
		 employeeResponseDTO.setId(employee.getId());
		 employeeResponseDTO.setName(employee.getName());
		 employeeResponseDTO.setTechnicalSkill(employee.getTechnicalSkill());		 
		 Set<EmployeeResponseDTO.ProjectResponseDTO> projectResponseDTOs = new HashSet<>();		 
		 for(Project pro : employee.getProjects()) {
			 EmployeeResponseDTO.ProjectResponseDTO projDTO = new EmployeeResponseDTO.ProjectResponseDTO();
			 projDTO.setId(pro.getId());
			 projDTO.setProjectName(pro.getProjectName());
			 projDTO.setTechnologyUsed(pro.getTechnologyUsed());
			 projectResponseDTOs.add(projDTO);
		 }
		 employeeResponseDTO.setProjects(projectResponseDTOs);
		 return employeeResponseDTO;
		 
	 }

	
}
