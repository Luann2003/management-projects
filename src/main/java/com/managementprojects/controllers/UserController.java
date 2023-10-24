package com.managementprojects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managementprojects.dto.UserDTO;
import com.managementprojects.entities.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {
	
	@Autowired
	private UserService service;

	@PreAuthorize("hasAnyRole('ROLE_MEMBER', 'ROLE_RESPONSIBLE', 'ROLE_ADMINISTRATOR')")
	@GetMapping(value = "/me")
	public ResponseEntity<UserDTO> findMe(){
		UserDTO dto = service.findMe();
		return ResponseEntity.ok().body(dto);
	}
}
