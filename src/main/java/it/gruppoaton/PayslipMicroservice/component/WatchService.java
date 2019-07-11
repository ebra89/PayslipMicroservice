package it.gruppoaton.PayslipMicroservice.component;


import it.gruppoaton.PayslipMicroservice.Utils.Validator;
import it.gruppoaton.PayslipMicroservice.model.Email;
import it.gruppoaton.PayslipMicroservice.services.EmployeeService;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
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

    @Autowired
    Validator validator;

    public static final String OBSERVED_FOLDER = "/home/dir/";

    @Async("watcher")
    public void run(){

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
                for(WatchEvent event : events) {
                    WatchEvent.Kind<?> kind = event.kind();
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();
                    if (kind == StandardWatchEventKinds.OVERFLOW) {
                        continue;
                    }
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        System.out.format("Creazione del file %s %n", fileName);
                        if(!(validator.PayslipFileNameValidator(fileName.getFileName().toString()))){
                            System.out.println("il payslip " + fileName + " non è stato slavato nome del file non corretto");
                        }else{
                            Email email = payslipService.storePayslip(OBSERVED_FOLDER+fileName);
                            if(email!=null) {
                                emailService.run(email);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }else{
                                System.out.println("il payslip " + fileName + " non è stato slavato");
                            }
                        }
                    }
                    if(kind == StandardWatchEventKinds.ENTRY_DELETE) {
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
