package it.gruppoaton.PayslipMicroservice.component;


import it.gruppoaton.PayslipMicroservice.Utils.BufferEmail;
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

    public static final String OBSERVED_FOLDER_STANDARD = "C:\\Users\\ATON User 5\\Desktop\\dir\\standard\\";
    public static final String OBSERVED_FOLDER_TREDICESIMA = "C:\\Users\\ATON User 5\\Desktop\\dir\\tredicesima\\";
    public static final String OBSERVED_FOLDER_COMPLEANNO = "C:\\Users\\ATON User 5\\Desktop\\dir\\compleanno\\";
    public static final String OBSERVED_FOLDER_FINERAPPORTO = "C:\\Users\\ATON User 5\\Desktop\\dir\\finerapporto\\";

    @Async("watcher")
    public void watcher(BufferEmail bufferEmail){


        Files.walkFileTree(){

        }

        Path pathStandard = Paths.get(OBSERVED_FOLDER_STANDARD);
        Path pathTredicesima = Paths.get(OBSERVED_FOLDER_TREDICESIMA);
        Path pathCompleanno = Paths.get(OBSERVED_FOLDER_COMPLEANNO);
        Path pathFineRapporto = Paths.get(OBSERVED_FOLDER_FINERAPPORTO);

        try{
            java.nio.file.WatchService watcher = FileSystems.getDefault().newWatchService();
            WatchKey keyStandard = pathStandard.register(watcher,  StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey keyTredicesima = pathTredicesima.register(watcher,  StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey keyCompleanno = pathCompleanno.register(watcher,  StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            WatchKey keyFineRapporto = pathFineRapporto.register(watcher,  StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            while(true) {

                try {
                    keyStandard = watcher.take();
                   } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<WatchEvent<?>> events = keyStandard.pollEvents();
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
                            Email email = payslipService.storePayslip(OBSERVED_FOLDER_STANDARD + fileName);
                            if(email!=null) {
                                bufferEmail.putEmail(email);
                            }else{
                                System.out.println("il payslip " + fileName + " non è stato slavato");
                            }
                        }
                    }
                    if(kind == StandardWatchEventKinds.ENTRY_DELETE) {
                        System.out.format("cancellazione del file %s %n", fileName);
                    }
                    if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                        //System.out.format("il file %s è stato modificato %n", fileName);
                    }
                    boolean valid = keyStandard.reset();
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
