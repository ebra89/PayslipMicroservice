package it.gruppoaton.PayslipMicroservice.controller;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.services.EmailService;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;

@Controller
public class EmailController {
	
	@Autowired
	private Employee employee;
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("sendEmail")
	public String notifyPayslip() throws MessagingException {
		
		employee.getEmail();
		
		try {
			emailService.sendEmail(employee);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		
		return "Email inviata";
	}
	
	 
	
	

}
