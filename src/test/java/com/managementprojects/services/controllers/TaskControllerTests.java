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
import com.managementprojects.dto.TaskDTO;
import com.managementprojects.factory.TaskFactory;
import com.managementprojects.utils.TokenUtil;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerTests {
	
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
	public void findAllShouldReturnUsersAdminLogged() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/task")
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content[0].id").isNotEmpty());
		result.andExpect(jsonPath("$.content[0].name").value("task 1"));
		result.andExpect(jsonPath("$.content[0].startDate").value("2020-07-13T20:50:07.123450Z"));
		result.andExpect(jsonPath("$.content[0].finishDate").value("2020-07-15T14:33:25.123450Z"));
	}
	
	@Test
	public void findByIdShouldReturnTaskDTOWhenIdExisting() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/task/{id}", existingId)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundExceptionWhenIdDoesNotExisting() throws Exception {
		

		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		ResultActions result =
				mockMvc.perform(get("/task/{id}", nonExistingId)
						.header("Authorization", "Bearer " + accessToken)
						.contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void insertTaskShouldStatusIsCreated() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		TaskDTO taskDTO = TaskFactory.createTaskDTO();
		
		String jsonBody = objectMapper.writeValueAsString(taskDTO);
		
		ResultActions result =
				mockMvc.perform(post("/task")
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	
	@Test
	public void updateShouldUpdateResourceWhenIdExists() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
		
		TaskDTO taskDTO = TaskFactory.createTaskDTO();
		
		String jsonBody = objectMapper.writeValueAsString(taskDTO);
		
		ResultActions result =
				mockMvc.perform(put("/task/{id}", existingId)
					.content(jsonBody)
					.header("Authorization", "Bearer " + accessToken)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	

	@Test
	public void updateShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);
			
		TaskDTO taskDTO = TaskFactory.createTaskDTO();
		String jsonBody = objectMapper.writeValueAsString(taskDTO);
		
		ResultActions result =
				mockMvc.perform(put("/task/{id}", nonExistingId)
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
				mockMvc.perform(delete("/task/{id}", independentId)
				.header("Authorization", "Bearer " + accessToken));
		
		
		result.andExpect(status().isNoContent());
	}

	@Test
	public void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, adminUsername, adminPassword);

		ResultActions result =
				mockMvc.perform(delete("/task/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken));
		

		result.andExpect(status().isNotFound());
	}

	@Test
	@Transactional(propagation = Propagation.SUPPORTS) 
	public void deleteShouldReturnForbbidenWhenMemberLogged() throws Exception {		
		
		String accessToken = tokenUtil.obtainAccessToken(mockMvc, memberUsername, memberPassword);

		
		ResultActions result =
				mockMvc.perform(delete("/task/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken));
				
		result.andExpect(status().isForbidden());
	}
}
