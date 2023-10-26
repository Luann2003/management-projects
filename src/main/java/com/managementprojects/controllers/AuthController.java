package com.managementprojects.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.managementprojects.dto.EmailDTO;
import com.managementprojects.dto.NewPasswordDTO;
import com.managementprojects.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
	
	@Autowired
	private AuthService authservice;
	
	@PostMapping(value = "recover-token")
	public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailDTO body) {
		authservice.createRecoverToken(body);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/new-password")
	public ResponseEntity<Void> saveNewPassword(@Valid @RequestBody NewPasswordDTO body) {
		authservice.saveNewPassword(body);
		return ResponseEntity.noContent().build();
	}

}
