package it.gruppoaton.PayslipMicroservice.component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import it.gruppoaton.PayslipMicroservice.Utils.Buffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	//private Buffer buffer;

	//public EmailService (Buffer buffer){this.buffer=buffer;}



			@Async("emailSender")
			public void run(Buffer buffer){

				System.out.println("email service partito!!");

				while (true){
					Employee employee = buffer.takeEmail().getEmployee();
					String subject = buffer.takeEmail().getSubject();
					String body = buffer.takeEmail().getBody();

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

}
