package it.gruppoaton.PayslipMicroservice.component;

import it.gruppoaton.PayslipMicroservice.Utils.Buffer;
import it.gruppoaton.PayslipMicroservice.entities.Employee;
import it.gruppoaton.PayslipMicroservice.model.Email;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
@Service
public class WatchService{

    @Autowired
    private PayslipService payslipService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmailService emailService;

    public static final String OBSERVED_FOLDER = "C:\\Users\\ATON User 5\\Desktop\\dir\\";

    //private Buffer buffer;

    //public WatchService(Buffer buffer){this.buffer=buffer;}


    @Async("watcher")
    public void run(){

        System.out.println("watcher partito!!");

        Path path = Paths.get(OBSERVED_FOLDER);

        try{
            java.nio.file.WatchService watcher = FileSystems.getDefault().newWatchService();
            WatchKey key = path.register(watcher,  StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        while(true) {

            try {
                key = watcher.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WatchEvent<?>> events = key.pollEvents();
            for (WatchEvent event : events) {
                WatchEvent.Kind<?> kind = event.kind();
                WatchEvent<Path> ev = (WatchEvent<Path>) event;
                Path fileName = ev.context();

                if (kind == StandardWatchEventKinds.OVERFLOW) {
                    continue;
                }

                if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                    System.out.format("Creazione del file %s %n", fileName);
                    Email email = payslipService.storePayslip(OBSERVED_FOLDER+fileName);
                    System.out.println(" sto prima del metodo putMail");
                    emailService.run(email);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }

                if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                    System.out.format("cancellazione del file %s %n", fileName);
                }

                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    System.out.format("il file %s è stato modificato %n", fileName);
                }

                boolean valid = key.reset();
                if (!valid) {
                    break;
                }
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
