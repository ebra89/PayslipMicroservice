package it.gruppoaton.PayslipMicroservice.component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import it.gruppoaton.PayslipMicroservice.entities.Employee;


@Component
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
		
		public void sendEmail(Employee employee,String subject, String body) throws MailException, MessagingException {


			MimeMessage mimeMessage = javaMailSender.createMimeMessage();

			JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setTo(employee.getEmail());
			helper.setSubject(subject);
			helper.setText(body);

			javaMailSender.send(mimeMessage);
			
		}

}
