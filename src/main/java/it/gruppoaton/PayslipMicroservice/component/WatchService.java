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
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static final String OBSERVED_FOLDER = "C:\\Users\\ATON User 5\\Desktop\\dir\\";
    private static Map<WatchKey, Path> watchKeyToPathMap = new HashMap<>();

    @Async("watcher")
    public void watcher(BufferEmail bufferEmail){

        try{
            java.nio.file.WatchService watchService = FileSystems.getDefault().newWatchService();
            watch (watchService, Paths.get(OBSERVED_FOLDER), bufferEmail);

        }catch (Exception e){}

/*
        if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
            System.out.format("Creazione del file %s %n", fileName);

        }

 */







    }

    private void watch(java.nio.file.WatchService watchService, Path start, BufferEmail bufferEmail) throws IOException, InterruptedException {

        registerTree(watchService, start);

        while(true){

            WatchKey key = watchService.take();
            List<WatchEvent<?>> events = key.pollEvents();
            for (WatchEvent event : events) {
                WatchEvent.Kind<?> kind= event.kind();
                Path pathEvent =(Path)event.context();
                //Path directory = watchKeyToPathMap.get(key);
                Path directory = (Path) key.watchable();
                Path child = directory.resolve(pathEvent);
                if (kind == StandardWatchEventKinds.ENTRY_CREATE && Files.isDirectory(child)){
                    registerTree(watchService, child);
                }
                if (kind == StandardWatchEventKinds.ENTRY_CREATE){
                    if (!Files.isDirectory(child)){
                        String fileName = child.getFileName().toString();
                        String extension = fileName.substring(fileName.length()-4);
                        System.out.printf("%s : %s \n", child, kind );
                        if (extension.toUpperCase().equals(".PDF")){

                            if(!(validator.PayslipFileNameValidator(fileName))){
                                System.out.println("il payslip " + fileName + " non è stato slavato nome del file non corretto");
                            }else{
                                Email email = payslipService.storePayslip(child);
                                if(email!=null) {
                                    bufferEmail.putEmail(email);
                                }else{
                                    System.out.println("il payslip " + fileName + " non è stato slavato");
                                }
                            }

                        }else{System.out.println("non è un pdf");}
                    }

                }
                System.out.printf("%s : %s \n", child, kind );
            }

            boolean valid = key.reset();
            if(!valid) {
                watchKeyToPathMap.remove(key);
                if (watchKeyToPathMap.isEmpty()) {
                    break;
                }
            }
        }
    }

    private static void registerTree(java.nio.file.WatchService watchService, Path start)throws IOException{
        Files.walkFileTree(start, new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                WatchKey key = dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
                watchKeyToPathMap.put(key, dir);
                return FileVisitResult.CONTINUE;
            }
        });


    }

}
