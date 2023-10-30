package com.managementprojects.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.managementprojects.dto.UserDTO;
import com.managementprojects.dto.UserInsertDTO;
import com.managementprojects.dto.UserUpdateDTO;
import com.managementprojects.entities.User;
import com.managementprojects.factory.UserDetailsFactory;
import com.managementprojects.factory.UserFactory;
import com.managementprojects.projections.UserDetailsProjection;
import com.managementprojects.repository.RoleRepository;
import com.managementprojects.repository.UserRepository;
import com.managementprojects.service.AuthService;
import com.managementprojects.service.UserService;
import com.managementprojects.service.exceptions.DatabaseException;
import com.managementprojects.service.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {
	
	@InjectMocks
	private UserService service;
	
	@Mock
	private AuthService authService;
	
	@Mock
	private UserRepository repository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
    public PasswordEncoder passwordEncoder;
	
	private String existingUsername, nonExistingUsername;
	private User user;
	private List<UserDetailsProjection> userDeatils;
	private PageImpl<User> page;
	private Long existingUserId, nonExistingUserId, dependentUserId;
	private String password;
	
	@BeforeEach
	void setup() throws Exception {
				
		existingUsername = "maria@gmail.com";
		nonExistingUsername = "user@gmail.com";
		
		existingUserId = 2L;
		nonExistingUserId = 1000L;
		dependentUserId = 5L;
		
		password = "123456";
		
		user = UserFactory.createUserEntity();

		page = new PageImpl<>(List.of(user));
		
		userDeatils  = UserDetailsFactory.createCustomClient(existingUsername);
		
		Mockito.when(repository.searchUserAndRolesByEmail(existingUsername)).thenReturn(userDeatils);
		Mockito.when(repository.searchUserAndRolesByEmail(nonExistingUsername)).thenReturn(new ArrayList<>());		
		
		Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(page);
		
		Mockito.when(repository.findById(existingUserId)).thenReturn(Optional.of(user));
		Mockito.when(repository.findById(nonExistingUserId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.save(any())).thenReturn(user);
		Mockito.when(passwordEncoder.encode(password)).thenReturn(user.getPassword());
		
		Mockito.when(repository.getReferenceById(existingUserId)).thenReturn(user);
		Mockito.when(repository.getReferenceById(nonExistingUserId)).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(repository).deleteById(existingUserId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentUserId);

		Mockito.when(repository.existsById(existingUserId)).thenReturn(true);
		Mockito.when(repository.existsById(nonExistingUserId)).thenReturn(false);
		Mockito.when(repository.existsById(dependentUserId)).thenReturn(true);
	}
	
	@Test
	public void authenticatedShouldReturnUserEntityWhenUserExists() {
		
		Mockito.when(authService.authenticated()).thenReturn(user);
		UserDTO result = service.findMe();
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
		
		Mockito.when(authService.authenticated()).thenThrow(new UsernameNotFoundException("User not found"));

		assertThrows(UsernameNotFoundException.class, () -> authService.authenticated());
	}
	
	@Test
	public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		
		UserDetails result = service.loadUserByUsername(existingUsername);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getUsername(), existingUsername);	
	}
	
	@Test
	public void loadUserByUsernameShouldUsernameNotFoundExceptionWhenUserDoesNotExists() {

		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(nonExistingUsername);
		});
	}
	
	@Test
	public void fildAllPagedShouldReturnPageUserDTO() {
		
		Pageable pageable = PageRequest.of(0,5);
		
		
		Page<UserDTO> result = service.findAllPaged(pageable);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.iterator().next().getName(), "Maria");
	}
	
	@Test
	public void findByIdShouldReturnUserDTOWhenIdExisting() {
		
		UserDTO result = service.findById(existingUserId);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingUserId);
	}
	
	@Test
	public void findByIdShouldReturnResourceNotFoundWhenIdNotExisting() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			@SuppressWarnings("unused")
			UserDTO result = service.findById(nonExistingUserId);
		});
	}
	
	@Test
	public void insertShouldReturnUserDTO() {
				
		UserInsertDTO dto = new UserInsertDTO();
		dto.setEmail("jose@gmail.com");
		dto.setName("José");
		dto.setPassword("123456");
		
		UserService serviceSpy = Mockito.spy(service);
		UserDTO result = serviceSpy.insert(dto);
		
		Assertions.assertNotNull(result);
	}
	
	@Test
	public void updateShouldReturnUserDTOWhenExistingId() {
		
		UserUpdateDTO dto = new UserUpdateDTO();
		dto.setEmail("jose@gmail.com");
		dto.setName("José");
		
		UserService serviceSpy = Mockito.spy(service);
		UserDTO result = serviceSpy.update(existingUserId,dto);
		
		Assertions.assertNotNull(result);
	}
	
	@SuppressWarnings("unused")
	@Test
	public void updateShouldReturnResourceNotFoundWhenIdDoesNotExisting() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			UserUpdateDTO dto = new UserUpdateDTO();
			dto.setEmail("jose@gmail.com");
			dto.setName("José");
			
			UserService serviceSpy = Mockito.spy(service);
			UserDTO result = serviceSpy.update(nonExistingUserId,dto);
		});
	}
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingUserId);
		});

		Mockito.verify(repository, times(1)).deleteById(existingUserId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingUserId);
		});
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentUserId);
		});
		Mockito.verify(repository, times(1)).deleteById(dependentUserId);
	}
}
