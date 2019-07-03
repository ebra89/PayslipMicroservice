package it.gruppoaton.PayslipMicroservice.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import it.gruppoaton.PayslipMicroservice.entities.Employee;


@Service
public class EmailService {
	
	private JavaMailSender javaMailSender;
	
	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender; 
		
	}
		
		public void sendEmail(Employee employee) throws MailException, MessagingException {

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(employee.getEmail());
			helper.setSubject("è online il tuo nuovo cedolino");
			helper.setText("Blablabla");

			javaMailSender.send(mimeMessage);
			
		}

}
