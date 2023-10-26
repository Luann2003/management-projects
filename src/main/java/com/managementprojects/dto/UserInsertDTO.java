package com.managementprojects.dto;

import com.managementprojects.service.validation.UserInsertValid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UserInsertValid
public class UserInsertDTO extends UserDTO {

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 6, message = "deve ter no minimo 6 caracteres")
	private String password;

	UserInsertDTO() {
		super();
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
