package com.managementprojects.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.managementprojects.dto.EmailDTO;
import com.managementprojects.entities.PasswordRecover;
import com.managementprojects.entities.User;
import com.managementprojects.factory.UserFactory;
import com.managementprojects.repository.PasswordRecoverRepository;
import com.managementprojects.repository.UserRepository;
import com.managementprojects.service.AuthService;
import com.managementprojects.service.EmailService;

@ExtendWith(SpringExtension.class)
public class AuthServiceTests {

	@InjectMocks
	private AuthService service;

	@Mock
	private UserRepository repository;

	@Mock
	private PasswordRecover passwordRecover;
	
	@Mock
	private PasswordRecoverRepository recoverRepository;

	@Mock
	private EmailService emailService;

	private String existingUsername, nonExistingUsername;

	private User user;
	private EmailDTO emailDTO;

	@BeforeEach
	void setup() throws Exception {

		existingUsername = "maria@gmail.com";
		nonExistingUsername = "user@gmail.com";
		
		emailDTO = new EmailDTO(existingUsername);

		user = UserFactory.createUserEntity();

		Jwt jwtPrincipal = mock(Jwt.class);
		when(jwtPrincipal.getClaim("username")).thenReturn(existingUsername);
		Authentication authentication = mock(Authentication.class);
		when(authentication.getPrincipal()).thenReturn(jwtPrincipal);

		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		Mockito.when(repository.findByEmail(existingUsername)).thenReturn(user);
		Mockito.when(repository.findByEmail(nonExistingUsername)).thenThrow(UsernameNotFoundException.class);
		

	}

	@Test
	public void authenticatedShouldReturnUserEntityWhenUserExists() {

		User result = service.authenticated();

		Assertions.assertNotNull(result);
	}

	@Test
	public void authenticatedShouldReturnUsernameNotFoundExceptionWhenUsernameNonExisting() {

		Authentication authentication = mock(Authentication.class);
		Jwt jwtPrincipal = mock(Jwt.class);
		when(jwtPrincipal.getClaim("username")).thenReturn(nonExistingUsername);
		when(authentication.getPrincipal()).thenReturn(jwtPrincipal);
		SecurityContext securityContext = mock(SecurityContext.class);
		SecurityContextHolder.setContext(securityContext);
		when(securityContext.getAuthentication()).thenReturn(authentication);

		Mockito.when(repository.findByEmail("invalidUsername")).thenReturn(null);

		Assertions.assertThrows(UsernameNotFoundException.class, () -> {
			service.authenticated();
		});
	}

	@Test
	public void createRecoverTokenShouldCreateTokenAndSendEmail() {

	}

}
