package com.managementprojects.services;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.managementprojects.repository.TaskRepository;
import com.managementprojects.service.TaskService;

@ExtendWith(SpringExtension.class)
public class TaskServiceTests {
	
	@InjectMocks
	private TaskService service;
	
	@Mock
	private TaskRepository repository;
	
	void setup() throws Exception {
		

	}

}
