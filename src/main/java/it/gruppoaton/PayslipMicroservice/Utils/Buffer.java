package it.gruppoaton.PayslipMicroservice.Utils;

import it.gruppoaton.PayslipMicroservice.model.Email;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class Buffer {

    private LinkedList<Email> coda = new LinkedList<>();
    private int sizeMax= 100;

    public Buffer(){

    }
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
    }
}
