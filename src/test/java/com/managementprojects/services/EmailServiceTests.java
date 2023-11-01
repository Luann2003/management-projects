package com.managementprojects.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.managementprojects.service.EmailService;
import com.managementprojects.service.exceptions.EmailException;

@ExtendWith(SpringExtension.class)
public class EmailServiceTests {

	@InjectMocks
	private EmailService emailService;

	@Mock
	private JavaMailSender emailSender;

	private String to, subject, body;

	@BeforeEach
	void setup() throws Exception {

		to = "maria@gmail.com";
		subject = "Recuperação de senha";
		body = "Acesse o link para definir uma nova senha";

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(body);

		Mockito.doNothing().when(emailSender).send(message);
		
	}

	@Test
	public void sendEmailReturnBodyEmail() {

		Assertions.assertDoesNotThrow(() -> {
			emailService.sendEmail(to, subject, body);
		});
	}

	@Test
	public void sendEmailReturnEmailExceptionWhenEmailDoesNotExisting() {
		
		Mockito.doThrow(new MailSendException("Failed to send email")).when(emailSender).send(Mockito.any(SimpleMailMessage.class));
		
		Assertions.assertThrows(EmailException.class, () -> {

			emailService.sendEmail(to, subject, body);
		});
	}

}
