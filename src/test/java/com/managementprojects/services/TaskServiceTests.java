package com.managementprojects.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.managementprojects.dto.TaskDTO;
import com.managementprojects.entities.Project;
import com.managementprojects.entities.Task;
import com.managementprojects.entities.User;
import com.managementprojects.factory.ProjectFactory;
import com.managementprojects.factory.TaskFactory;
import com.managementprojects.factory.UserFactory;
import com.managementprojects.repository.ProjectRepository;
import com.managementprojects.repository.TaskRepository;
import com.managementprojects.repository.UserRepository;
import com.managementprojects.service.AuthService;
import com.managementprojects.service.TaskService;
import com.managementprojects.service.exceptions.DatabaseException;
import com.managementprojects.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class TaskServiceTests {
	
	@InjectMocks
	private TaskService service;
	
	@Mock
	private TaskRepository repository;
	
	@Mock
	private ProjectRepository projectRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private AuthService authService;
	
	private Long existingId, nonExistingId, dependentId;
	
	private Long existingUserId, nonExistingUserId;
	
	private PageImpl<Task> page;
	
	private Task task;
	private TaskDTO taskDTO;
	private Project project;
	private User user;

	@BeforeEach
	void setup() throws Exception {
		
		existingId = 1L;
		nonExistingId = 100L;
		dependentId = 50L;
		
		existingUserId = 2L;
		nonExistingUserId = 101L;
		
		
		task = TaskFactory.createTask();
		taskDTO = TaskFactory.createTaskDTO();
		user = UserFactory.createUserEntity();
		project = ProjectFactory.CreatedProject();
		
		page = new PageImpl<>(List.of(task));
		String name = "Task 1";
		
		Mockito.when(repository.search02(eq(name), any())).thenReturn(page);	
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(task));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(projectRepository.getReferenceById(existingId)).thenReturn(project);
		Mockito.when(projectRepository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(userRepository.getReferenceById(existingUserId)).thenReturn(user);
		Mockito.when(userRepository.getReferenceById(nonExistingUserId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(task);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(repository.save(any())).thenReturn(task);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
		
		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentId)).thenReturn(true);	
}

	@Test
	public void findAllShouldReturnPagedProjectDTO() {

		Pageable pageable = PageRequest.of(0, 5);
		String name = "Task 1";
		
		Page<TaskDTO> result = service.findAll(name, pageable);
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void findByIdShouldReturnTaskDTOWhenIdExists() {
		
		TaskDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			TaskDTO result = service.findById(nonExistingId);
		});
	}
	
	@Test
	public void insertShouldReturnTaskDTO() {

		TaskService serviceSpy = Mockito.spy(service);
		TaskDTO result = serviceSpy.insert(taskDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldReturnTaskDTOWhenIdExists() {

		TaskService serviceSpy = Mockito.spy(service);
		TaskDTO result = serviceSpy.update(existingId, taskDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		TaskService serviceSpy = Mockito.spy(service);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			TaskDTO result = serviceSpy.update(nonExistingId, taskDTO);
		});
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		Mockito.verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});
		Mockito.verify(repository, times(1)).deleteById(dependentId);
	}	
}
