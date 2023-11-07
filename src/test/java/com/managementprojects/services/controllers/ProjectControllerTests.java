package com.managementprojects.services.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

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
import com.managementprojects.entities.Project;
import com.managementprojects.utils.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProjectControllerTests {
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String adminUsername, adminPassword;
	
	private Long existingId, nonExistingId;
	
	@BeforeEach
	void setUp() throws Exception {
		
		existingId = 1L;
		nonExistingId = 100L;
		
		adminUsername = "verfute2005@gmail.com";
		adminPassword = "123456";

	}
	
	@Test
	public void findAllShouldReturnUsersAdminLogged() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/projects")
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").isNotEmpty());
		result.andExpect(jsonPath("$.content[0].name").value("test 1"));
		result.andExpect(jsonPath("$.content[0].startDate").value("2020-07-13T20:50:07.123450Z"));
		result.andExpect(jsonPath("$.content[0].finishDate").value("2020-07-15T14:33:25.123450Z"));
		result.andExpect(jsonPath("$.content[0].tasks[0].id").isNotEmpty());
	}
	
	@Test
	public void findByIdShouldReturnProjectDTOWhenIdExisting() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/projects/{id}", existingId)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExisting() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/projects/{id}", nonExistingId)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertProjectShouldStatusIsCreated() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		Project project = new Project(10L, "project test", "description test", Instant.parse("2023-12-29T23:59:59Z"), Instant.parse("2023-12-30T23:59:59Z"));
		String jsonBody = objectMapper.writeValueAsString(project);
		
		ResultActions result =
				mockMvc.perform(post("/projects")
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	
	@Test
	public void updateShouldUpdateResourceWhenIdExists() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
			
		Project project = new Project(10L, "project test", "description test", Instant.parse("2023-12-29T23:59:59Z"), Instant.parse("2023-12-30T23:59:59Z"));
		String jsonBody = objectMapper.writeValueAsString(project);
			
		ResultActions result =
				mockMvc.perform(put("/projects/{id}", existingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
			
		Project project = new Project(10L, "project test", "description test", Instant.parse("2023-12-29T23:59:59Z"), Instant.parse("2023-12-30T23:59:59Z"));
		String jsonBody = objectMapper.writeValueAsString(project);
			
		ResultActions result =
				mockMvc.perform(put("/projects/{id}", nonExistingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void deleteShouldReturnNoContentWhenIndependentId() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		Long independentId = 4L;
		
		ResultActions result =
				mockMvc.perform(delete("/projects/{id}", independentId)
				.header("Authorization", "Bearer " + accessToken));
		
		
		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		ResultActions result =
				mockMvc.perform(delete("/projects/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken));
		

		result.andExpect(status().isNotFound());
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS) 
	public void deleteShouldReturnBadRequestWhenDependentId() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		Long dependentId = 1L;	
		ResultActions result =
				mockMvc.perform(delete("/projects/{id}", dependentId)
				.header("Authorization", "Bearer " + accessToken));
				
		result.andExpect(status().isBadRequest());
	}
}
