package com.managementprojects.services.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managementprojects.dto.NewPasswordDTO;
import com.managementprojects.service.EmailService;
import com.managementprojects.utils.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private TokenUtil tokenUtil;

	@Autowired
	private ObjectMapper objectMapper;

	private String memberUsername, memberPassword;
	private String adminUsername, adminPassword;

	private Long existingId, nonExistingId;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = 100L;

		adminUsername = "verfute2005@gmail.com";
		adminPassword = "123456";

		memberUsername = "maria@gmail.com";
		memberPassword = "123456";

	}

	@Test
	public void createRecoverTokenShouldReturnNoContent() throws Exception {
//
//		EmailDTO email = new EmailDTO(adminUsername);
//
//		String jsonBody = objectMapper.writeValueAsString(email);
//
//		ResultActions result = mockMvc.perform(post("/auth/recover-token").content(jsonBody)
//				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
//
//		result.andExpect(status().isNoContent());
		
		
	}

	@Test
	public void saveNewPasswordShouldReturnIsOk() throws Exception {
		
//		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
//	    
//		
//		NewPasswordDTO newPasswordDTO = new NewPasswordDTO();
//		newPasswordDTO.setToken(accessToken);
//		newPasswordDTO.setPassword("abcd1234");
//
//		String jsonBody = objectMapper.writeValueAsString(newPasswordDTO);
//
//		ResultActions result = mockMvc.perform(put("/auth/new-password")
//				.content(jsonBody)
//				.contentType(MediaType.APPLICATION_JSON)
//				.accept(MediaType.APPLICATION_JSON));
//
//		result.andExpect(status().isNoContent());
	}

}
