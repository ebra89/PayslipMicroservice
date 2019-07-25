package it.gruppoaton.PayslipMicroservice.component;

import it.gruppoaton.PayslipMicroservice.Utils.BufferEmail;
import it.gruppoaton.PayslipMicroservice.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/*
@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

			@Async("emailSender")
			public void emailSender(BufferEmail bufferEmail){

				System.out.println("email service partito!!");
				while(true) {
					Email email = bufferEmail.getEmail();
					Employee employee = email.getEmployee();
					String firstName = email.getEmployee().getFirstName();
					String lastName = email.getEmployee().getLastName();
					String subject = email.getSubject();
					String body = email.getBody();

					MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

					MimeMessageHelper helper = null;
					try {
						helper = new MimeMessageHelper(mimeMessage, true);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					try {
						helper.setTo(employee.getEmail());
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					try {
						helper.setTo(firstName.toUpperCase());
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					try {
						helper.setTo(lastName.toUpperCase());
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					try {
						helper.setSubject(subject);
					} catch (MessagingException e) {
						e.printStackTrace();
					}
					try {
						helper.setText(body);
					} catch (MessagingException e) {
						e.printStackTrace();
					}


					javaMailSender.send(mimeMessage);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}


				}
			}



}

 */


@Component
public class EmailService{

	@Autowired
	public JavaMailSender javaMailSender;


	@Async("emailSender")
	public void emailSender(BufferEmail bufferEmail){

		while (true) {
			Email email = bufferEmail.getEmail();
			String to = email.getEmployee().getEmail();
			String subject = email.getSubject();
			String text = email.getBody();

			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

			simpleMailMessage.setTo(to);
			simpleMailMessage.setSubject(subject);
			simpleMailMessage.setText(text);

			try {
				javaMailSender.send(simpleMailMessage);

				} catch (Exception e) {
					System.out.println("mail a " + to + "non inviata" + e.getMessage());
				}
				System.out.println("inviata email " + to);
			}

		}
}