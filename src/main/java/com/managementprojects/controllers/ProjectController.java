package com.managementprojects.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/projects")
public class ProjectController {
	
	@Autowired
	private ProjectService service;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_RESPONSIBLE', 'ROLE_MEMBER')")
	@GetMapping
	public ResponseEntity<List<ProjectDTO>> findAll(){
		List<ProjectDTO> result = service.findAll();
		return ResponseEntity.ok().body(result);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_RESPONSIBLE', 'ROLE_MEMBER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<ProjectDTO> findById(@PathVariable Long id) {
	    ProjectDTO dto = service.findById(id);
	     return ResponseEntity.ok(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_RESPONSIBLE', 'ROLE_MEMBER')")
	@PostMapping
	public ResponseEntity<ProjectDTO> insert(@Valid @RequestBody ProjectDTO dto){
		dto = service.insert(dto);
		 URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
	                .buildAndExpand(dto.getId()).toUri();
		 return ResponseEntity.created(uri).body(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_RESPONSIBLE')")
	@PutMapping(value = "/{id}")
	public ResponseEntity<ProjectDTO> update(@PathVariable Long id, @RequestBody ProjectDTO dto){
		 dto = service.update(id, dto);
	     return ResponseEntity.ok(dto);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMINISTRATOR', 'ROLE_RESPONSIBLE')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ProjectDTO> delete(@PathVariable Long id){
		service.delete(id);
        return ResponseEntity.noContent().build();
	}

}
