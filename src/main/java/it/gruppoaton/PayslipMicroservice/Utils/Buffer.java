package it.gruppoaton.PayslipMicroservice.Utils;

import it.gruppoaton.PayslipMicroservice.model.Email;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class Buffer {

    private static Buffer instance = null;
    private Buffer(){}
    public static Buffer getInstance(){
        if (instance == null){
            instance = new Buffer();
        }
        return  instance;
    }

    private LinkedList<Email> coda = new LinkedList<>();
    private int sizeMax= 2;


    public boolean isFull(){
        return coda.size() >= sizeMax;
    }
    public boolean isEmpty(){
        return coda.isEmpty();
    }
    public synchronized Email takeEmail(){
        while (isEmpty()){
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        Email email = coda.removeFirst();
        notifyAll();
        return email;
    }
    public synchronized void putEmail(Email email){
        while (isFull()){
            try {
                wait();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        coda.addLast(email);

        notifyAll();
        for (Email e:coda) {
            System.out.println("email: " + e.getSubject()+" "+e.getEmployee().getFiscalCode()+" "+e.getEmployee().getEmail());

        }
    }

    public List<Email> takeCoda(){
        return this.coda;
    }
}
