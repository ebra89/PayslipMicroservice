package it.gruppoaton.PayslipMicroservice;

import it.gruppoaton.PayslipMicroservice.Utils.Buffer;
import it.gruppoaton.PayslipMicroservice.component.EmailService;
import it.gruppoaton.PayslipMicroservice.component.WatchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PayslipMicroserviceApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(PayslipMicroserviceApplication.class, args);
		Buffer buffer = new Buffer();
		Thread watchService =new Thread(new WatchService(buffer));
		Thread emailService = new Thread(new EmailService(buffer));
		watchService.start();
		emailService.start();



	}

}
