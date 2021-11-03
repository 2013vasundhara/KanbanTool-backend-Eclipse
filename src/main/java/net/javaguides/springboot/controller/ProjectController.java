package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Project;
import net.javaguides.springboot.repository.ProjectRepository;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class ProjectController {

	@Autowired
	private ProjectRepository projectRepository;
	
	// get all employees
	@GetMapping("/project")
	public List<Project> getAllProjects(){
		return projectRepository.findAll();
	}		
	
	// create employee rest api
	@PostMapping("/project")
	public Project createProject(@RequestBody Project project) {
		return projectRepository.save(project);
	}
	
	// get employee by id rest api
	@GetMapping("/project/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not exist with id :" + id));
		return ResponseEntity.ok(project);
	}
	
	// update employee rest api
	
	@PutMapping("/project/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails){
		Project project = projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not exist with id :" + id));
		
		project.setProjectName(projectDetails.getProjectName());
		project.setEmployeeName(projectDetails.getEmployeeName());
		project.setStatus(projectDetails.getStatus());
		
		Project updatedProject = projectRepository.save(project);
		return ResponseEntity.ok(updatedProject);
	}
	
	// delete employee rest api
	@DeleteMapping("/project/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteProject(@PathVariable Long id){
		Project project=projectRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Project not exist with id :" + id));
		
		projectRepository.delete(project);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	
}
