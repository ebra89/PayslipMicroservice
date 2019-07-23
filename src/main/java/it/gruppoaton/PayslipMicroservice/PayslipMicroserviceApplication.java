package it.gruppoaton.PayslipMicroservice;

import it.gruppoaton.PayslipMicroservice.Utils.BufferEmail;
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
	public TaskExecutor getAsyncExecutorWatcher(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-Watcher_");
		return executor;

	}

	@Autowired
	private EmailService emailService;

	@Bean("emailSender")
	public TaskExecutor getAsyncExecutorEmailSender(){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-EmailSender_");
		return executor;

	}

	private BufferEmail bufferEmail = new BufferEmail(10, 4000);


	public static void main(String[] args){
		SpringApplication.run(PayslipMicroserviceApplication.class, args);

	}


	@Override
	public void run(String... args) throws Exception {
		watchService.watcher(bufferEmail);
		emailService.emailSender(bufferEmail);

	}
}
