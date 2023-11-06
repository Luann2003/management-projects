package com.managementprojects.services.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.managementprojects.entities.User;
import com.managementprojects.utils.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

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
	public void findAllShouldReturnUnauthorizedWhenNotValidToken() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/users")
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void findAllShouldReturnUsersAdminLogged() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/users")
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnUnauthorizedWhenNotValidToken() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/users/{id}", existingId)
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void findByIdShouldReturnUsersAdminLogged() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/users/{id}", existingId)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExisting() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/users/{id}", nonExistingId)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findMeShouldReturnUserLogged() throws Exception {
		
		  String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		    ResultActions result = mockMvc.perform(get("/users/me")
		            .header("Authorization", "Bearer " + accessToken)
		            .contentType(MediaType.APPLICATION_JSON));

		    result.andExpect(status().isOk());
		    result.andExpect(jsonPath("$.id").value(3L));
		    result.andExpect(jsonPath("$.email").value(adminUsername));
		    result.andExpect(jsonPath("$.name").value("Luan Victor"));

	}
	@Test
	public void findmeShouldReturnUnauthorizedWhenNotValidToken() throws Exception {
		
		ResultActions result =
				mockMvc.perform(get("/users/me")
					.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void insertShouldStatusIsCreated() throws Exception {
		
		User user = new User(existingId, "Rafael", "rafael@gmail.com", "123456");
		String jsonBody = objectMapper.writeValueAsString(user);
		
		ResultActions result =
				mockMvc.perform(post("/users")
					.content(jsonBody)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	
	@Test
	public void updateShouldUpdateResourceWhenIdExists() throws Exception {
		
	 String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		
		User user = new User(existingId, "Rafael", "rafael@gmail.com", "123456");
		String jsonBody = objectMapper.writeValueAsString(user);
		
		ResultActions result =
				mockMvc.perform(put("/users/{id}", existingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
	 String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		
		User user = new User(existingId, "Rafael", "rafael@gmail.com", "123456");
		String jsonBody = objectMapper.writeValueAsString(user);
		
		ResultActions result =
				mockMvc.perform(put("/users/{id}", nonExistingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateShouldReturnForbbidenWhenLoggedMember() throws Exception {
		
	 String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		
		User user = new User(existingId, "Rafael", "rafael@gmail.com", "123456");
		String jsonBody = objectMapper.writeValueAsString(user);
		
		ResultActions result =
				mockMvc.perform(put("/users/{id}", existingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isForbidden());
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIndependentId() throws Exception {
		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		Long independentId = 4L;
		
		ResultActions result =
				mockMvc.perform(delete("/users/{id}", independentId)
				.header("Authorization", "Bearer " + accessToken));
		
		
		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		ResultActions result =
				mockMvc.perform(delete("/users/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken));
		

		result.andExpect(status().isNotFound());
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS) 
	public void deleteShouldReturnBadRequestWhenDependentId() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		Long dependentId = 1L;	
		ResultActions result =
				mockMvc.perform(delete("/users/{id}", dependentId)
				.header("Authorization", "Bearer " + accessToken));
				
		result.andExpect(status().isBadRequest());
	}
	
}
