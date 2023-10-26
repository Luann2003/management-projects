package com.managementprojects.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class NewPasswordDTO {

	@NotBlank(message = "Campo obrigatório")
	@Size(min = 8, message = "Minimo 6 caracteres")
	private String password;
	@NotBlank(message = "Campo obrigatório")
	private String token;

	public NewPasswordDTO() {
	}

	public NewPasswordDTO(String password, String token) {
		this.password = password;
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}