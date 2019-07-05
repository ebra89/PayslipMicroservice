package it.gruppoaton.PayslipMicroservice.component;

import it.gruppoaton.PayslipMicroservice.Utils.Buffer;
import it.gruppoaton.PayslipMicroservice.services.PayslipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component("watchService")
public class WatchService implements Runnable{


    @Autowired
    Buffer buffer;

    @Autowired
    PayslipService payslipService;

    public WatchService(Buffer buffer) {
        this.buffer = buffer;
    }

    public static final String OBSERVED_FOLDER = "C:\\Users\\ATON User 5\\Desktop\\dir";


    @Override
    public void run(){

        Path path = Paths.get(OBSERVED_FOLDER);

        try{
            java.nio.file.WatchService watcher = FileSystems.getDefault().newWatchService();
            WatchKey key = path.register(watcher,  StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);
        while(true) {
            System.out.println("watch service partit");
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
                    try {

                        payslipService.storePayslip(OBSERVED_FOLDER+fileName, buffer);
                    }catch (Exception ex){
                        System.out.println(ex.getMessage()+" eccezione ");
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
