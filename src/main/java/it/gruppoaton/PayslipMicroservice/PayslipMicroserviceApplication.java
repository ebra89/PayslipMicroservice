package it.gruppoaton.PayslipMicroservice;


import it.gruppoaton.PayslipMicroservice.Utils.Buffer;
import it.gruppoaton.PayslipMicroservice.component.EmailService;
import it.gruppoaton.PayslipMicroservice.component.WatchService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@SpringBootApplication
@EnableAsync
public class PayslipMicroserviceApplication implements CommandLineRunner {

	@Autowired
	private WatchService watchService;

	@Bean("watcher")
	public TaskExecutor getAsyncExecutor(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;

	}
	@Autowired
	private EmailService emailService;

	@Bean("emailSender")
	public TaskExecutor getAsyncEmailSender(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;

	}

	Buffer buffer = Buffer.getInstance();


	public static void main(String[] args){
		SpringApplication.run(PayslipMicroserviceApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		watchService.run(buffer);
		emailService.run(buffer);
	}
}
