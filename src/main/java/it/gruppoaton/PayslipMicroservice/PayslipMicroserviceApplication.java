package it.gruppoaton.PayslipMicroservice;

import it.gruppoaton.PayslipMicroservice.component.WatchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PayslipMicroserviceApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(PayslipMicroserviceApplication.class, args);
		WatchService watchService = (WatchService)applicationContext.getBean("watchService");
		watchService.run();

	}

}
