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
		//WatchService watchService = (WatchService)applicationContext.getBean("watchService");
		//watchService.run();
		Buffer buffer =new Buffer(100);
		Thread emailService=new Thread(new EmailService(buffer));
		Thread watchService=new Thread(new WatchService(buffer));

	}

}
