package it.gruppoaton.PayslipMicroservice.component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


import it.gruppoaton.PayslipMicroservice.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


import it.gruppoaton.PayslipMicroservice.entities.Employee;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;


			public void run(Email email){

				System.out.println("email service partito!!");


					Employee employee = email.getEmployee();
					String subject = email.getSubject();
					String body = email.getBody();

					MimeMessage mimeMessage = javaMailSender.createMimeMessage();
					JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

					MimeMessageHelper helper = null;
					try {
						helper = new MimeMessageHelper(mimeMessage,true);
					}catch (MessagingException e){
						e.printStackTrace();
					}
					try {
						helper.setTo(employee.getEmail());
					}catch (MessagingException e){
						e.printStackTrace();
					}
					try {
						helper.setSubject(subject);
					}catch (MessagingException e){
						e.printStackTrace();
					}
					try {
						helper.setText(body);
					}catch (MessagingException e){
						e.printStackTrace();
					}
					javaMailSender.send(mimeMessage);

			}

}
