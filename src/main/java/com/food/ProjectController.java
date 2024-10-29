package com.food;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;
	
	@Autowired
	ProjectService projectService;
	
	@GetMapping("/all")
	public List<Project> getAllEmployees() {
		return projectService.getAllEmployees();
	}
	
	
	@PostMapping("/add")
	public ResponseEntity<Project> addProjectWithEmployees(@RequestBody ProjectDTO projectDTO) {
	    Project createdProject = projectService.addProjectWithEmployees(projectDTO);
	    return ResponseEntity.ok(createdProject);
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
	    Optional<Project> project = projectRepository.findById(id);
	    if (project.isPresent()) {
	        ProjectResponseDTO projectResponseDTO = projectService.convertToDTO(project.get());
	        return ResponseEntity.ok(projectResponseDTO);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}





}
