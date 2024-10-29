package com.food;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	EmployeeService employeeService;
	
    @Autowired
    private ProjectRepository projectRepository;
	
	@GetMapping("/all")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}
	
	
	
	 @PostMapping("/create")
	    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
	        Employee employee = new Employee();
	        employee.setName(employeeDTO.getName());
	        employee.setEmail(employeeDTO.getEmail());
	        employee.setTechnicalSkill(employeeDTO.getTechnicalSkill());

	        // Fetch the Project entities based on projectIds from DTO
	        Set<Project> projects = new HashSet<>();
	        for (Long projectId : employeeDTO.getProjectIds()) {
	            projectRepository.findById(projectId).ifPresent(projects::add);
	        }
	        employee.setProjects(projects);

	        Employee savedEmployee = employeeService.saveEmployee(employee);
	        return ResponseEntity.ok(savedEmployee);
	    }
	 
	

		
		
		@GetMapping("/{id}")
		public ResponseEntity<EmployeeResponseDTO> getEmployeesById(@PathVariable Long id) {
		    Optional<Employee> project = employeeRepository.findById(id);
		    if (project.isPresent()) {
		    	EmployeeResponseDTO projectResponseDTO = employeeService.convertToDTO(project.get());
		        return ResponseEntity.ok(projectResponseDTO);
		    } else {
		        return ResponseEntity.notFound().build();
		    }
		}

}
