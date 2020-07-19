package com.example.demo.controllers;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/emails")
@AllArgsConstructor
public class SendEmailController {

	private final JavaMailSender emailSender;

	@PostMapping("/send")
	public void sendEmail() throws MessagingException {
		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
		String htmlMsg = "<u>asdasd</u><h3>Hello World!</h3>";
		mimeMessage.setContent(htmlMsg, "text/html"); /** Use this or below line **/
//		helper.setText(htmlMsg); // Use this or above line.
		helper.setTo("someone@abc.com");
		helper.setSubject("This is the test message for testing gmail smtp server using spring mail");
		helper.setFrom("abc@gmail.com");
		emailSender.send(mimeMessage);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("totoafrica@gmail.com");
		message.setSubject("Super toto");
		message.setText("<h1>Hello from JavaMailSender</h1>");
		emailSender.send(message);
	}
}
