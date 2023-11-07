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

import com.managementprojects.dto.ProjectDTO;
import com.managementprojects.entities.Project;
import com.managementprojects.factory.ProjectFactory;
import com.managementprojects.repository.ProjectRepository;
import com.managementprojects.service.AuthService;
import com.managementprojects.service.ProjectService;
import com.managementprojects.service.exceptions.DatabaseException;
import com.managementprojects.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProjectServiceTests {
	
	@InjectMocks
	private ProjectService service;
	
	@Mock
	private ProjectRepository repository;
	
	@Mock
	private AuthService authService;
	
	private Long existingId, nonExistingId, dependentId;
	
	private PageImpl<Project> page;
	
	private Project project;
	private ProjectDTO projectDTO;
	
	@BeforeEach
	void setup() throws Exception {

		existingId = 1L;
		nonExistingId = 100L;
		dependentId = 2L;
		
		project = ProjectFactory.CreatedProject();
		projectDTO = ProjectFactory.CreatedProjectDTO();
		page = new PageImpl<>(List.of(project));
		
		String name = "UPA 24";
		
		Mockito.when(repository.searchByName(eq(name), any())).thenReturn(page);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(project));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.save(any())).thenReturn(project);
		
		Mockito.when(repository.getReferenceById(existingId)).thenReturn(project);
		Mockito.when(repository.getReferenceById(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);

		Mockito.when(repository.existsById(existingId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentId)).thenReturn(true);
	}
	
	@Test
	public void findAllShouldReturnPagedProjectDTO() {

		Pageable pageable = PageRequest.of(0, 5);
		String name = "UPA 24";
		
		Page<ProjectDTO> result = service.findAll(name, pageable);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getName(), name);
	}
	
	@Test
	public void findByIdShouldReturnProjectDTOWhenIdExists() {
		
		ProjectDTO result = service.findById(existingId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			ProjectDTO result = service.findById(nonExistingId);
		});
	}
	
	@Test
	public void insertShouldReturnMovieDTO() {

		ProjectService serviceSpy = Mockito.spy(service);
		ProjectDTO result = serviceSpy.insert(projectDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}

	@Test
	public void updateShouldReturnProjectDTOWhenIdExists() {

		ProjectService serviceSpy = Mockito.spy(service);
		ProjectDTO result = serviceSpy.update(existingId, projectDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingId);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		ProjectService serviceSpy = Mockito.spy(service);

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			ProjectDTO result = serviceSpy.update(nonExistingId, projectDTO);
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
