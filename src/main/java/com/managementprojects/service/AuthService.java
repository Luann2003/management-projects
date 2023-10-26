package com.managementprojects.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.managementprojects.dto.EmailDTO;
import com.managementprojects.dto.NewPasswordDTO;
import com.managementprojects.entities.PasswordRecover;
import com.managementprojects.entities.User;
import com.managementprojects.repository.PasswordRecoverRepository;
import com.managementprojects.repository.UserRepository;
import com.managementprojects.service.exceptions.ResourceNotFoundException;

@Service
public class AuthService {
	
	@Value("${email.password-recover.token.minutes}")
	private Long tokenMinutes;

	@Value("${email.password-recover.uri}")
	private String recoverUri;

	@Autowired
	private PasswordRecoverRepository recoverRepository;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository repository;

	protected User authenticated() {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Jwt jwtPrincipal = (Jwt) authentication.getPrincipal();
			String username = jwtPrincipal.getClaim("username");
			return repository.findByEmail(username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Invalid user");
		}
	}
	
	@Transactional
	public void createRecoverToken(EmailDTO body) {

		User user = repository.findByEmail(body.getEmail());
		if (user == null) {
			throw new ResourceNotFoundException("Email não encontrado.");
		}

		PasswordRecover entity = new PasswordRecover();

		String token = UUID.randomUUID().toString();

		entity.setEmail(body.getEmail());
		entity.setToken(token);
		entity.setExpiration(Instant.now().plusSeconds(tokenMinutes * 60L));

		entity = recoverRepository.save(entity);

		String text = "Acesse o link para definir uma nova senha\n\n" + recoverUri + token + ". validade de "
				+ tokenMinutes + " minutos";

		emailService.sendEmail(body.getEmail(), "Recuperação de senha", text);

	}

	@Transactional
	public void saveNewPassword(NewPasswordDTO body) {
		List<PasswordRecover> result = recoverRepository.searchValidTokens(body.getToken(), Instant.now());
		if (result.size() == 0) {
			throw new ResourceNotFoundException("token invalido");
		}

		User user = repository.findByEmail(result.get(0).getEmail());
		user.setPassword(passwordEncoder.encode(body.getPassword()));
		user = repository.save(user);

	}
	
}
