package it.gruppoaton.PayslipMicroservice.Component;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Component
public class WatchService implements Runnable{

    @Override
    public void run(){
        // creaiamo un oggeto path che contenga il percorso della directory da monitorare

        Path path = Paths.get("/home/andrea/Scrivania/WatchDir");

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
                    String fileNameString=fileName.toFile().getName();
                    String fiscalCode=fileNameString.substring(fileNameString.length()-17);
                    byte[] byteFile = Files.readAllBytes(fileName);



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
