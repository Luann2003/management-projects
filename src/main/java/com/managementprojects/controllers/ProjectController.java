package com.managementprojects.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.managementprojects.dto.ProjectDTO;
import com.managementprojects.entities.service.ProjectService;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService service;
	
	@GetMapping
	public ResponseEntity<List<ProjectDTO>> findAll(){
		List<ProjectDTO> result = service.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProjectDTO> findById(@PathVariable Long id) {
	    ProjectDTO dto = service.findById(id);
	     return ResponseEntity.ok(dto);
	}
	 
	@PostMapping
	public ResponseEntity<ProjectDTO> insert(@RequestBody ProjectDTO dto){
		dto = service.insert(dto);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(dto.getId()).toUri();
		 return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @RequestBody ProjectDTO dto){
		 dto = service.update(id, dto);
	     return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProjectDTO> delete(@PathVariable Long id){
		service.delete(id);
        return ResponseEntity.noContent().build();
	}

}
